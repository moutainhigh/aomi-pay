package com.cloudbest.user.controller;


import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.user.entity.City;
import com.cloudbest.user.entity.CustomerAddr;
import com.cloudbest.user.service.CityService;
import com.cloudbest.user.vo.CityVO;
import com.cloudbest.user.vo.CustomerAddrVO;
import net.sf.json.JSONObject;
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
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * 根据等级和父级id查询地址列表
     */
    @RequestMapping(value = "/app/user/listCity",method = RequestMethod.POST)
    public Result listCity(@RequestBody City city){
        List<City> cities;
        try{
            cities = cityService.listCity(city);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,cities);
    }

    /**
     * 查询城市列表
     */
    @RequestMapping(value = "/user/city/queryList",method = RequestMethod.POST)
    public Result queryList(@RequestBody CityVO vo){
        List<City> cities;
        try{
            cities = cityService.queryList(vo);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,cities);
    }

    /**
     * 根据ID查询城市信息
     */
    @RequestMapping(value = "/user/city/queryById",method = RequestMethod.POST)
    public Result queryById(@RequestBody CityVO vo) throws Exception{
        City city;
        try{
            city = cityService.queryById(vo.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,city);
    }
}
