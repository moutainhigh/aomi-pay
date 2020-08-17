package com.aomi.pay.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: hdq
 * @Description: dozer 转换器   对象与对象的转换
 * @Date: 2020/7/8 13:30
 * @Version: 1.0
 */
public class GeneralConvertorUtil {

    //持有Dozer单例, 避免重复创建DozerMapper消耗资源.
    @Resource
    public static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * 如果需要转换的对象属性与接收的对象属性不同，则在被转换的实体类的属性上加 @Mapper("属性名")
     *  比如User 转成 UserVo
     *  User中为name   UserVo中为userName
     *  要把name转成userName  则在User中name属性上加 @Mapping("userName")
     */


    /**
     * List  实体类 转换器
     * @param source 原数据
     * @param clz    转换类型
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> List<T> convertor(List<S> source, Class<T> clz) {
        if (source == null) {
            return null;
        }
        List<T> map = new ArrayList<>();
        for (S s : source) {
            map.add(mapper.map(s, clz));
        }
        return map;
    }

    /**
     * Set 实体类 深度转换器
     * @param source 原数据
     * @param clz    目标对象
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> Set<T> convertor(Set<S> source, Class<T> clz) {
        if (source == null) {
            return null;
        }
        Set<T> set = new TreeSet<>();
        for (S s : source) {
            set.add(mapper.map(s, clz));
        }
        return set;
    }

    /**
     * 实体类 深度转换器
     * @param source
     * @param clz
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> T convertor(S source, Class<T> clz) {
        if (source == null) {
            return null;
        }
        return mapper.map(source, clz);
    }

    public static void convertor(Object source, Object object) {
        mapper.map(source, object);
    }

    public static <T> void copyConvertor(T source, Object object) {
        mapper.map(source, object);
    }

    /**
     * @author hdq
     * @date 2020/8/11
     * @Param list<T> keywords 匹配关键字  指定匹配的属性名
     * @desc 在List<T>里边过滤出模糊匹配到的List<T>并返回
     **/
    public static <T> List<T> fuzzyQuery(List<T> list, String keywords, String name) throws Exception {
        List<T> fuzzyQuery = new LinkedList<>();
        long time1 = System.currentTimeMillis();
        //大小写不敏感的时候，多加一个条件
        Pattern pattern = Pattern.compile(keywords, Pattern.CASE_INSENSITIVE);
        for (T t : list) {
            String value = t.getClass().getMethod(name).invoke(t).toString();
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                fuzzyQuery.add(t);
            }
        }
        long time2 = System.currentTimeMillis();
        System.out.println("时间差异:");
        System.out.print(time2 - time1);
        return fuzzyQuery;
    }

}
