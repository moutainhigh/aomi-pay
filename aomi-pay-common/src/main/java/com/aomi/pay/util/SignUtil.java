//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.aomi.pay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.iboxpay.open.security.cache.AppSecretCacheItem;
import com.iboxpay.open.security.cache.SecretCacheManager;
import com.iboxpay.open.security.constant.SignType;
import com.iboxpay.open.security.sign.SignParser;
import com.iboxpay.open.security.sign.SignParserFactory;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;


public class SignUtil {

    public SignUtil() {
    }

    public static String toURLKeyValueText(Map<String, String> map, String secretKey) {
        assert map != null && !map.isEmpty();

        Map<String, String> sortMap = new TreeMap(Comparator.naturalOrder());
        sortMap.putAll(map);
        sortMap.remove("x-sign");
        StringBuilder builder = new StringBuilder();
        sortMap.forEach((key, value) -> {
            if (StringUtils.isNotBlank(value)) {
                builder.append(key).append("=").append(value).append("&");
            }

        });
        String prepareText = builder.toString().replaceAll("&$", "");
        if (StringUtils.isBlank(secretKey)) {
            return prepareText;
        } else {
            String signType = (String)map.get("x-sign-type");
            Optional<SignType> signTypeOptional = Stream.of(SignType.values()).filter((t) -> {
                return t.name().equals(signType);
            }).findFirst();
            SignType signType1 = (SignType)signTypeOptional.orElseThrow(() -> {
                return new SecurityException("不支持签名算法类型, type=" + signType);
            });
            switch(signType1) {
                case MD5:
                    return String.format("%s%s%s", secretKey, prepareText, secretKey);
                case HMAC_SHA256:
                    return String.format("%s&key=%s", prepareText, secretKey);
                default:
                    return prepareText;
            }
        }
    }

    public static String sign(Map<String, String> map, String secretKey) {
        assert map != null && map.size() > 0 && StringUtils.isNotBlank(secretKey);

        return findSignParser((String)map.get("x-sign-type")).sign(toURLKeyValueText(map, secretKey), secretKey);
    }

    public static String sign(Map<String, String> map, byte[] secretKey) {
        assert map != null && map.size() > 0 && secretKey != null && secretKey.length > 0;

        return findSignParser((String)map.get("x-sign-type")).sign(toURLKeyValueText(map, new String(secretKey, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8), secretKey);
    }

    public static Map<String, String> sign(Map<String, String> map, String appId, String appSecret) {
        return sign(map, appId, appSecret, SignType.HMAC_SHA256);
    }

    public static Map<String, String> sign(Map<String, String> map, String appId, String appSecret, SignType signType) {
        map.put("up-appId", appId);
        map.put("timestamp", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        map.put("x-sign-type", signType.name());
        String sign = sign(map, appSecret);
        map.put("x-sign", sign);
        return map;
    }

    private static SignParser findSignParser(String type) {
        Optional<SignType> signTypeOptional = Stream.of(SignType.values()).filter((t) -> {
            return t.name().equals(type);
        }).findFirst();
        SignType signType = (SignType)signTypeOptional.orElseThrow(() -> {
            return new SecurityException("不支持签名算法类型, type=" + type);
        });
        return SignParserFactory.newInstance(signType);
    }

    public static boolean verify(Map<String, String> map, String secretKey) {
        assert map != null && map.size() > 0 && StringUtils.isNotBlank(secretKey);

        String sign = (String)map.get("x-sign");
        if (StringUtils.isBlank(sign)) {
            throw new SecurityException("找不到签名参数, param=x-sign");
        } else {
            return findSignParser((String)map.get("x-sign-type")).verify(toURLKeyValueText(map, secretKey), sign, secretKey);
        }
    }

    public static boolean verify(Map<String, String> map, byte[] secretKey) {
        assert map != null && map.size() > 0 && secretKey != null && secretKey.length > 0;

        String sign = (String)map.get("x-sign");
        if (StringUtils.isBlank(sign)) {
            throw new SecurityException("找不到签名参数, param=x-sign");
        } else {
            return findSignParser((String)map.get("x-sign-type")).verify(toURLKeyValueText(map, new String(secretKey, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8), sign, secretKey);
        }
    }

    public static boolean verify(String paramJsonString, String secretKey) {
        assert StringUtils.isNotBlank(paramJsonString);

        Map<String, String> map = (Map)JSON.parseObject(paramJsonString, new TypeReference<Map<String, String>>() {
        }, new Feature[]{Feature.DisableCircularReferenceDetect});
        return verify(map, secretKey);
    }

    public static boolean verify(String paramJsonString, byte[] secretKey) {
        assert StringUtils.isNotBlank(paramJsonString);

        Map<String, String> map = (Map)JSON.parseObject(paramJsonString, new TypeReference<Map<String, String>>() {
        }, new Feature[]{Feature.DisableCircularReferenceDetect});
        return verify(map, secretKey);
    }

    public static String sign(Map<String, String> map) {
        assert map != null && map.size() > 0;

        String appId = (String)map.get("up-appId");
        if (StringUtils.isBlank(appId)) {
            throw new SecurityException("找不到参数, param=up-appId");
        } else {
            return signByAppId(map, appId);
        }
    }

    public static String signByAppId(Map<String, String> map, String appId) {
        assert map != null && map.size() > 0 && StringUtils.isNotBlank(appId);

        AppSecretCacheItem secretCacheItem = SecretCacheManager.getInstance().getSecretByAppId(appId);

        try {
            return findSignParser((String)map.get("x-sign-type")).sign(toURLKeyValueText(map, secretCacheItem.getAppSecretBase64()).getBytes(StandardCharsets.UTF_8), secretCacheItem.getAppSecretBytes());
        } catch (Exception var4) {
            throw new SecurityException(var4);
        }
    }

    public static String signByAppId(String paramJson, String appId) {
        assert StringUtils.isNotBlank(paramJson);

        Map<String, String> map = (Map)JSON.parseObject(paramJson, new TypeReference<Map<String, String>>() {
        }, new Feature[]{Feature.DisableCircularReferenceDetect});
        return signByAppId(map, appId);
    }

    public static boolean verify(String paramJsonString) {
        assert StringUtils.isNotBlank(paramJsonString);

        Map<String, String> map = (Map)JSON.parseObject(paramJsonString, new TypeReference<Map<String, String>>() {
        }, new Feature[]{Feature.DisableCircularReferenceDetect});
        return verify(map);
    }

    public static boolean verify(Map<String, String> map) {
        assert map != null && map.size() > 0;

        String sign = (String)map.get("x-sign");
        if (StringUtils.isBlank(sign)) {
            throw new SecurityException("找不到签名参数, param=x-sign");
        } else {
            String appId = (String)map.get("up-appId");
            if (StringUtils.isBlank(appId)) {
                throw new SecurityException("找不到参数, param=up-appId");
            } else {
                AppSecretCacheItem secretCacheItem = SecretCacheManager.getInstance().getSecretByAppId(appId);
                return findSignParser((String)map.get("x-sign-type")).verify(toURLKeyValueText(map, secretCacheItem.getAppSecretBase64()).getBytes(StandardCharsets.UTF_8), sign, secretCacheItem.getAppSecretBytes());
            }
        }
    }
}
