package com.aomi.pay.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author hdq
 * @Date 2020/8/25
 * @Description: Redis分布式锁
 */
@Component
@Slf4j
public class RedisLock {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final int TIMEOUT = 60;//超时时间 60s

    /**
     * 加锁
     *
     * @param key   唯一标志
     * @param value 当前时间+超时时间 也就是时间戳
     * @return
     */
    public boolean lock(String key, String value) {
        //对应setnx命令
        //key,value,过期时间,时间单位 s
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value, TIMEOUT, TimeUnit.SECONDS)) {
            //可以成功设置,也就是key不存在
            return true;
        } else {
            return false;
        }

    }


    /**
     * 解锁
     *
     * @param key   key
     */
    public void unlock(String key) {
        try {
            stringRedisTemplate.opsForValue().getOperations().delete(key);
        } catch (Exception e) {
            log.error("[Redis分布式锁] 解锁出现异常了", e);
        }
    }

}