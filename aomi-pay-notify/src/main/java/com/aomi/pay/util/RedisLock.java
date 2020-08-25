package com.aomi.pay.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        //if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value, TIMEOUT, TimeUnit.SECONDS)) {
            //可以成功设置,也就是key不存在
            return true;
        } else {
            return false;
        }

        //判断锁超时 - 防止原来的操作异常，没有运行解锁操作  防止死锁
        //String currentValue = stringRedisTemplate.opsForValue().get(key);
        //如果锁过期  currentValue不为空且小于当前时间
        //if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
        //获取上一个锁的时间value  对应getset，如果key存在
        //String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);

        //假设两个线程同时进来这里，因为key被占用了，而且锁过期了。获取的值currentValue=A(get取的旧的值肯定是一样的),两个线程的value都是B,key都是K.锁时间已经过期了。
        //而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的value已经变成了B。只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
        //log.info("oldValue:{}",oldValue);
        //if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
        //oldValue不为空且oldValue等于currentValue，也就是校验是不是上个对应的时间戳，也是防止并发
        //  return true;
        //}
        //}
        //return false;
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