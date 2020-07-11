package com.cloudbest.user.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.PhoneUtil;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.user.entity.CustomerAddr;
import com.cloudbest.user.mapper.CustomerAddrMapper;
import com.cloudbest.user.service.CustomerAddrService;
import com.cloudbest.user.vo.CustomerAddrVO;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户地址表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@RestController
@CrossOrigin
public class CustomerAddrController {
    private static Logger log = LoggerFactory.getLogger(CustomerAddrController.class);

    @Autowired
    private CustomerAddrService customerAddrService;
    @Autowired
    private CustomerAddrMapper customerAddrMapper;

    /**
     * 根据用户id查询地址
     */
    @RequestMapping(value = "/app/user/queryAddr/{token}",method = RequestMethod.POST)
    public Result queryAddr(@PathVariable("token")String token) throws Exception{
        Long customerId = null;
        try {
            customerId = TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        List<CustomerAddr> customerAddrs;
        try{
            customerAddrs = customerAddrService.queryAddrs(customerId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,customerAddrs);
    }
    /**
     * 根据用户id查询地址
     */
    @RequestMapping(value = "/user/queryAddr",method = RequestMethod.POST)
    public Result queryAddr(@Param("customerId") Long customerId) throws Exception{

        List<CustomerAddr> customerAddrs;
        try{
            customerAddrs = customerAddrService.queryAddrs(customerId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,customerAddrs);
    }

    /**
     * 添加用户地址
     */
    @RequestMapping(value = "/app/user/addAddr/{token}",method = RequestMethod.POST)
    public Result addAddr(@RequestBody JSONObject str, @PathVariable("token")String token) throws Exception{
        log.info("添加用户地址入参："+ JSON.toJSONString(str.getJSONObject("customerAddr")));
        Long customerId = null;
        try {
            customerId = TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        if (customerAddrMapper.selectCount(new LambdaQueryWrapper<CustomerAddr>().eq(CustomerAddr::getCustomerId,customerId))>=10){
            return new Result(900136,"收货地址已达到上限!",false);
        }

        JSONObject customerObject = str.getJSONObject("customerAddr");
        String phone = customerObject.getString("phone");
        if (!PhoneUtil.isMobileSimple(phone)){
            return new Result(900123,"手机号码格式不正确",false);
        }
        String name = customerObject.getString("name");
        if (name==null){
            return new Result(900125,"收货人姓名为空",false);
        }
        CustomerAddr customerAddr = new CustomerAddr();
        String ssq = customerObject.getString("ssq");
        String[] ssqs = ssq.split("-");
        customerAddr.setProvince(ssqs[0]);
        customerAddr.setCity(ssqs[1]);
        customerAddr.setDistrict(ssqs[2]);
        customerAddr.setIsDefault(customerObject.getInt("isDefault"));
        customerAddr.setAddress(customerObject.getString("address"));
        customerAddr.setName(name);
        customerAddr.setPhone(phone);
        customerAddr.setCustomerId(customerId);
        try{
            customerAddrService.addAddr(token,customerAddr);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 修改用户默认地址
     */
    @RequestMapping(value = "/app/user/updateDef/{token}",method = RequestMethod.POST)
    public Result updateDef(@RequestBody JSONObject str, @PathVariable("token")String token) throws Exception {
        try {
            TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        Integer id = str.getInt("id");
        try {
            customerAddrService.updateDef(token,id);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }
    /**
     * 删除地址
     */
    @RequestMapping(value = "/app/user/delateAddr/{token}",method = RequestMethod.POST)
    public Result delateAddr(@RequestBody JSONObject str, @PathVariable("token")String token)throws Exception{
        Integer id = str.getInt("id");
        try {
            TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        try {
            customerAddrService.delateAddr(id);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }
    /**
     * 修改地址
     */
    @RequestMapping(value = "/app/user/updateAddr/{token}",method = RequestMethod.POST)
    public Result updateAddr(@RequestBody JSONObject str, @PathVariable("token")String token)throws Exception{
        log.info("修改用户地址入参："+ JSON.toJSONString(str.getJSONObject("customerAddr")));
        try {
            TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        CustomerAddr customerAddr = new CustomerAddr();
        JSONObject customerObject = str.getJSONObject("customerAddr");
        String ssq = customerObject.getString("ssq");
        String[] ssqs = ssq.split("-");
        log.error("修改用户地址token："+ TokenUtil.getUserId(token));
        log.error("修改用户地址前："+ JSON.toJSONString(str.getJSONObject("customerAddr")));
        customerAddr.setId(customerObject.getInt("id"));
        customerAddr.setProvince(ssqs[0]);
        customerAddr.setCity(ssqs[1]);
        customerAddr.setDistrict(ssqs[2]);
        customerAddr.setIsDefault(customerObject.getInt("isDefault"));
        customerAddr.setAddress(customerObject.getString("address"));
        customerAddr.setName(customerObject.getString("name"));
        customerAddr.setPhone(customerObject.getString("phone"));
        try{
            customerAddrService.updateAddr(token,customerAddr);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询地址列表
     */
    @RequestMapping(value = "/user/addr/queryList",method = RequestMethod.POST)
    public Result queryAddr(@RequestBody CustomerAddrVO vo) throws Exception{
        List<CustomerAddr> customerAddrs;
        try{
            customerAddrs = customerAddrService.queryList(vo);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,customerAddrs);
    }

    /**
     * 查询地址列表
     */
    @RequestMapping(value = "/user/addr/queryById",method = RequestMethod.POST)
    public Result queryById(@RequestBody CustomerAddrVO vo) throws Exception{
        CustomerAddr customerAddr;
        try{
            customerAddr = customerAddrService.queryById(vo.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,customerAddr);
    }
}
