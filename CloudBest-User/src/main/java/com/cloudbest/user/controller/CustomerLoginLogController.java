package com.cloudbest.user.controller;


import com.cloudbest.user.service.CustomerLoginLogService;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户登陆日志表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("user")
public class CustomerLoginLogController {

    @Autowired
    private CustomerLoginLogService customerLoginLogService;
}
