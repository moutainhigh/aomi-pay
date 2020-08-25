package com.aomi.pay.config;

import com.aomi.pay.enums.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @Author hdq
 * @Date 2020/8/18
 * @Version 1.0
 */
@Component
@Order(value = -100)
@Slf4j
@Aspect
public class DataSourceSwitchAspect {

    @Pointcut("execution(* com.aomi.pay.mapper.order..*.*(..))")
    private void orderAspect() {
    }

    @Pointcut("execution(* com.aomi.pay.mapper.user..*.*(..))")
    private void userAspect() {
    }

    @Before("orderAspect()")
    public void order() {
        DbContextHolder.setDbType(DBTypeEnum.order);
    }

    @Before("userAspect()")
    public void user() {
        DbContextHolder.setDbType(DBTypeEnum.user);
    }

}
