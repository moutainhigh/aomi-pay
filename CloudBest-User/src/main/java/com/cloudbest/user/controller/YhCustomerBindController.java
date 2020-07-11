package com.cloudbest.user.controller;


import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.user.entity.City;
import com.cloudbest.user.entity.YhCustomerBind;
import com.cloudbest.user.service.CityService;
import com.cloudbest.user.service.YhCustomerBindService;
import com.cloudbest.user.vo.CityVO;
import com.cloudbest.user.vo.CustomerAddrVO;
import com.cloudbest.user.vo.YhCustomerBindVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@RestController
@CrossOrigin
public class YhCustomerBindController {

    @Autowired
    private YhCustomerBindService customerBindService;

    /**
     * 新增友惠用户绑定信息
     */
    @RequestMapping(value = "/user/yh/addYh",method = RequestMethod.POST)
    public Result addYh(@RequestBody YhCustomerBind customerBind){
        try{
            customerBindService.creatNewCustomerBind(customerBind);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 修改友惠用户绑定信息
     */
    @RequestMapping(value = "/user/yh/updateYh",method = RequestMethod.POST)
    public Result updateYh(@RequestBody YhCustomerBind customerBind){
        try{
            customerBindService.updateCustomerBind(customerBind);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 逻辑删除友惠用户绑定信息
     */
    @RequestMapping(value = "/user/yh/offYh",method = RequestMethod.POST)
    public Result offYh(@RequestBody YhCustomerBind customerBind){
        try{
            customerBindService.offCustomerBind(customerBind.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询友惠用户绑定列表
     */
    @RequestMapping(value = "/user/yh/queryList",method = RequestMethod.POST)
    public Result queryList(@RequestBody YhCustomerBindVO vo){
        List<YhCustomerBind> customerBinds;
        try{
            customerBinds = customerBindService.queryList(vo);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,customerBinds);
    }

    /**
     * 查询友惠用户绑定详情信息
     */
    @RequestMapping(value = "/user/yh/queryById",method = RequestMethod.POST)
    public Result queryById(@RequestBody YhCustomerBindVO vo) throws Exception{
        YhCustomerBind customerBind;
        try{
            customerBind = customerBindService.queryById(vo.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,customerBind);
    }
}
