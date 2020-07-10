package com.cloudbest.user.mapper;

import com.cloudbest.user.entity.City;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Repository
public interface CityMapper extends BaseMapper<City> {
    @Select("select * from `city` where `level` = #{level} and `parent_id` = #{parentId}")
    List<City> listCity(int level,int parentId);

}
