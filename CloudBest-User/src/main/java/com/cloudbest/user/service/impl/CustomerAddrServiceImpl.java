package com.cloudbest.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.user.entity.CustomerAddr;
import com.cloudbest.user.entity.CustomerInf;
import com.cloudbest.user.mapper.CustomerAddrMapper;
import com.cloudbest.user.mapper.CustomerInfMapper;
import com.cloudbest.user.service.CustomerAddrService;
import com.cloudbest.user.vo.CustomerAddrVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class CustomerAddrServiceImpl implements CustomerAddrService {
    @Autowired
    private CustomerAddrMapper customerAddrMapper;
    @Autowired
    private CustomerInfMapper customerInfMapper;
    @Override
    public void addAddr(String token ,CustomerAddr customerAddr) throws Exception {
        if (token==null){
            throw new BusinessException(CommonErrorCode.E_900121.getCode(),"token信息为空");
        }
        if (customerAddr==null){
            throw new BusinessException(CommonErrorCode.E_900116.getCode(),"地址为空");
        }
        Long customerId = TokenUtil.getUserId(token);
        customerAddr.setCustomerId(customerId);
        int isDef = customerAddr.getIsDefault();
        customerAddr.setIsDefault(0);
        customerAddr.setModifiedTime(LocalDateTime.now());
        customerAddrMapper.insert(customerAddr);
        if (isDef==1){
            QueryWrapper<CustomerAddr> queryWrapper = new QueryWrapper();
            queryWrapper.eq("customer_id", customerId);
            queryWrapper.orderByDesc("modified_time");
            List<CustomerAddr> customerAddrs = customerAddrMapper.selectList(queryWrapper);
            Integer id = null ;
            if (customerAddrs.size()>0){
                 id = customerAddrs.get(0).getId();
            }
            updateDef(token,id);
        }
    }

    @Override
    public void updateDef(String token, Integer id) throws Exception {
        if (token==null){
            throw new BusinessException(CommonErrorCode.E_900121.getCode(),"token信息为空");
        }
        Long userId = TokenUtil.getUserId(token);
        if (id==null){
            throw new BusinessException(CommonErrorCode.E_900101.getCode(),"地址id为空");
        }
        CustomerAddr def = customerAddrMapper.selectOne(new LambdaQueryWrapper<CustomerAddr>().eq(CustomerAddr::getCustomerId,userId).eq(CustomerAddr::getIsDefault,1));
        if(def!=null){
            def.setIsDefault(0);
            def.setModifiedTime(LocalDateTime.now());
            customerAddrMapper.updateById(def);
        }
        CustomerAddr customerAddr = customerAddrMapper.selectOne(new LambdaQueryWrapper<CustomerAddr>().eq(CustomerAddr::getCustomerId,userId).eq(CustomerAddr::getId,id));
        customerAddr.setIsDefault(1);
        customerAddr.setModifiedTime(LocalDateTime.now());
        customerAddrMapper.updateById(customerAddr);
    }

    @Override
    public List<CustomerAddr> queryList(CustomerAddrVO info) {
        //分页参数
        int current =0;
        int size =0;
        if (info.getCurrent()!=null&&info.getSize()!=null){
            current = info.getCurrent();
            size = info.getSize();
        }else {
            current = 0;
            size = 200;
        }
        //查询可用图片
        QueryWrapper<CustomerAddr> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getCustomerId()!=null){
            queryWrapper.eq("customer_id", info.getCustomerId());
        }
        if (info.getIsDefault()!=null){
            queryWrapper.eq("is_default", info.getIsDefault());
        }
        Page<CustomerAddr> page = new Page<>(current, size);
        IPage<CustomerAddr> customerAddrList = customerAddrMapper.selectPage(page, queryWrapper);
        if (customerAddrList.getTotal()>0){
            return customerAddrList.getRecords();
        }
        return new ArrayList<>();
    }

    @Override
    public CustomerAddr queryById(Integer id) {
        CustomerAddr customerAddr = customerAddrMapper.selectById(id);
        return customerAddr;
    }

    @Override
    public void delateAddr(Integer id){
        if (id==null){
            throw new BusinessException(CommonErrorCode.E_900101.getCode(),"地址id为空");
        }
        customerAddrMapper.delete(new LambdaQueryWrapper<CustomerAddr>().eq(CustomerAddr::getId,id));
    }

    @Override
    public void updateAddr(String token, CustomerAddr customerAddr) throws Exception {
        if (token==null){
            throw new BusinessException(CommonErrorCode.E_900121.getCode(),"token信息为空");
        }
        if (customerAddr==null){
            throw new BusinessException(CommonErrorCode.E_900116.getCode(),"地址为空");
        }
        Long customerId = TokenUtil.getUserId(token);
        customerAddr.setCustomerId(customerId);
        int isDef = customerAddr.getIsDefault();
        customerAddr.setIsDefault(0);
        customerAddr.setModifiedTime(LocalDateTime.now());
        customerAddrMapper.updateById(customerAddr);
        if (isDef==1){
            QueryWrapper<CustomerAddr> queryWrapper = new QueryWrapper();
            queryWrapper.eq("customer_id", customerId);
            queryWrapper.orderByDesc("modified_time");
            List<CustomerAddr> customerAddrs = customerAddrMapper.selectList(queryWrapper);
            Integer id = null ;
            if (customerAddrs.size()>0){
                id = customerAddrs.get(0).getId();
            }
            updateDef(token,id);
        }

    }

    @Override
    public List<CustomerAddr> queryAddrs(Long customerId){
        if(customerId==null){
            throw new BusinessException(CommonErrorCode.E_900117.getCode(),"用户id为空");
        }
        Integer count = customerInfMapper.selectCount(new LambdaQueryWrapper<CustomerInf>().eq(CustomerInf::getCustomerId,customerId));
        if(count==0){
            throw new BusinessException(CommonErrorCode.E_900118.getCode(),"未找到该用户");
        }
        List<CustomerAddr> customerAddrs = customerAddrMapper.selectList(new LambdaQueryWrapper<CustomerAddr>().eq(CustomerAddr::getCustomerId,customerId));
        return customerAddrs;
    }

}
