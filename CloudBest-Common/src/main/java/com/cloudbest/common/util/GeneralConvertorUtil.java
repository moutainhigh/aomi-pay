package com.cloudbest.common.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @ProjectName: cloudbest
 * @Package: com.cloudbest.common.domain
 * @ClassName: GeneralConvertor
 * @Author: hdq
 * @Description: dozer 转换器
 * @Date: 2020/7/8 13:30
 * @Version: 1.0
 */
public class GeneralConvertorUtil {

    //持有Dozer单例, 避免重复创建DozerMapper消耗资源.
    @Resource
    public static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /*如果需要转换的对象属性与被转换的对象属性不同，则在被转换的实体类的属性上加 @Mapper("属性名")
     *  比如User 转成 UserVo
     *  User中为name   UserVo中为userName
     *  要把name转成userName  则在User中name属性上加 @Mapping("userName")
     */


    /**
     * List  实体类 转换器
     *
     * @param source 原数据
     * @param clz    转换类型
     * @param <T>
     * @param <S>
     * @return
     */
    public <T, S> List<T> convertor(List<S> source, Class<T> clz) {
        if (source == null) return null;
        List<T> map = new ArrayList<>();
        for (S s : source) {
            map.add(mapper.map(s, clz));
        }
        return map;
    }

    /**
     * Set 实体类 深度转换器
     *
     * @param source 原数据
     * @param clz    目标对象
     * @param <T>
     * @param <S>
     * @return
     */
    public <T, S> Set<T> convertor(Set<S> source, Class<T> clz) {
        if (source == null) return null;
        Set<T> set = new TreeSet<>();
        for (S s : source) {
            set.add(mapper.map(s, clz));
        }
        return set;
    }

    /**
     * 实体类 深度转换器
     *
     * @param source
     * @param clz
     * @param <T>
     * @param <S>
     * @return
     */
    public <T, S> T convertor(S source, Class<T> clz) {
        if (source == null) return null;
        return mapper.map(source, clz);
    }

    public void convertor(Object source, Object object) {
        mapper.map(source, object);
    }

    public <T> void copyConvertor(T source, Object object) {
        mapper.map(source, object);
    }

}
