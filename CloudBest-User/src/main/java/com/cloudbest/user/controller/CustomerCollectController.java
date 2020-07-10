package com.cloudbest.user.controller;




import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.user.entity.CustomerAddr;
import com.cloudbest.user.entity.CustomerCollect;
import com.cloudbest.user.service.CustomerCollectService;
import com.cloudbest.user.vo.CustomerAddrVO;
import com.cloudbest.user.vo.CustomerCollectVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 会员收藏的商品 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@RestController
@CrossOrigin
public class CustomerCollectController {

    @Autowired
    private CustomerCollectService customerCollectService;
    /**
     * 根据用户id查询收藏
     */
    @RequestMapping(value = "/app/user/queryCollect/{token}",method = RequestMethod.POST)
    public Result queryCollect(@PathVariable("token")String token ) throws Exception{
        try {
            TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        List<Result> collects;
        try {
             collects = customerCollectService.queryCollect(token);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,collects);
    }

    /**
     * 添加用户收藏
     * @param customerCollect
     * @return
     */
    @RequestMapping(value = "/app/user/addCollect/{token}",method = RequestMethod.POST)
    public Result addCollect(@RequestBody CustomerCollect customerCollect, @PathVariable("token")String token)throws Exception   {
        Long customerId;
        try {
            customerId = TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        customerCollect.setCustomerId(customerId);
        try {
            customerCollectService.addCollect(customerCollect);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }
    /**
     * 删除用户收藏
     */
    @RequestMapping(value = "/app/user/delateCollect/{token}",method = RequestMethod.POST)
    public Result delateCollect(@RequestBody JSONObject str, @PathVariable("token")String token)throws Exception{
        try {
            TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        Integer id = str.getInt("id");
        try {
            customerCollectService.delateCollect(id);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询收藏列表
     */
    @RequestMapping(value = "/user/collect/queryList",method = RequestMethod.POST)
    public Result queryAddr(@RequestBody CustomerCollectVO vo) throws Exception{
        List<CustomerCollect> customerCollects;
        try{
            customerCollects = customerCollectService.queryList(vo);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,customerCollects);
    }

    /**
     * 查询收藏商品详情
     */
    @RequestMapping(value = "/user/collect/queryById",method = RequestMethod.POST)
    public Result queryById(@RequestBody CustomerCollectVO vo) throws Exception{
        CustomerCollect customerCollect;
        try{
            customerCollect = customerCollectService.queryById(vo.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,customerCollect);
    }
}
