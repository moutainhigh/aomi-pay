package com.cloudbest.user.mapper;

import com.cloudbest.user.entity.CustomerLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 用户登录表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Repository
@MapperScan("com.cloudbest.user.mapper.xml")
public interface CustomerLoginMapper extends BaseMapper<CustomerLogin> {
    @Update("update `customer_login` set `password` = #{newPsw} where `mobile_phone` = #{phone}")
    void updatePsw(String phone,String newPsw);
}
