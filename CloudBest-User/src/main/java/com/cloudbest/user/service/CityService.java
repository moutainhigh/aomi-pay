package com.cloudbest.user.service;

import com.cloudbest.user.entity.City;
import com.cloudbest.user.vo.CityVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */

public interface CityService  {
    /**
     * 根据级别和父级id查询所在地
     */
    List<City> listCity(City city);

    List<City> queryList(CityVO vo);

    City queryById(Integer id);
}
