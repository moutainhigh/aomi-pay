package com.cloudbest.common.util;

import com.cloudbest.common.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 主数据系统标识
     */
    public static final String KEY_PREFIX = "SJ_PAY_PARAM";
    /**
     * 分割字符，默认[:]，使用:可用于rdm分组查看
     */
    private static final String KEY_SPLIT_CHAR = ":";

    /**
     * redis的key键规则定义
     * @param module 模块名称
     * @param func 方法名称
     * @param args 参数..
     * @return key
     */
    public static String keyBuilder(String module, String func, String... args) {
        return keyBuilder(null, module, func, args);
    }

    /**
     * redis的key键规则定义
     * @param module 模块名称
     * @param func 方法名称
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(String module, String func, String objStr) {
        return keyBuilder(null, module, func, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func 方法名称
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String objStr) {
        return keyBuilder(prefix, module, func, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func 方法名称
     * @param args 参数..
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String... args) {
        // 项目前缀
        if (prefix == null) {
            prefix = KEY_PREFIX;
        }
        StringBuilder key = new StringBuilder(prefix);
        // KEY_SPLIT_CHAR 为分割字符
        key.append(KEY_SPLIT_CHAR).append(module).append(KEY_SPLIT_CHAR).append(func);
        for (String arg : args) {
            key.append(KEY_SPLIT_CHAR).append(arg);
        }
        return key.toString();
    }

    /**
     * redis的key键规则定义
     * @param redisEnum 枚举对象
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(RedisEnum redisEnum, String objStr) {
        return keyBuilder(redisEnum.getKeyPrefix(), redisEnum.getModule(), redisEnum.getFunc(), objStr);
    }

    /**
     * 缓存限制用户连续请求时间
     * @param cacheName
     * @param cacheTime
     * @param keys
     * @return
     */
    public Map<String,Object> restClientLimit(String cacheName, long cacheTime, String ...keys){
        Map<String,Object> map = new HashMap<String,Object>();
        boolean hasKey = false;
        String key = cacheName+":";
        for(int i = 0;i < keys.length;i++){
            key = key + keys[i];
        }
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            map.put(CommonConstants.RESP_CODE, CommonConstants.FALIED);
            map.put(CommonConstants.RESP_MESSAGE, "操作过于频繁,请"+cacheTime+"秒后重试!");
            return map;
        }
        operations.set(key, key, cacheTime, TimeUnit.SECONDS);
        map.put(CommonConstants.RESP_CODE, CommonConstants.SUCCESS);
        map.put(CommonConstants.RESP_MESSAGE, "验证通过");
        return map;
    }
}