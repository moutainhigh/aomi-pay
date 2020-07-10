package com.cloudbest.user.service;

import com.cloudbest.user.entity.City;
import com.cloudbest.user.entity.YhCustomerBind;
import com.cloudbest.user.vo.CityVO;
import com.cloudbest.user.vo.YhCustomerBindVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */

public interface YhCustomerBindService {

    YhCustomerBind creatNewCustomerBind(YhCustomerBind customerBind);

    YhCustomerBind updateCustomerBind(YhCustomerBind customerBind);

    List<YhCustomerBind> queryList(YhCustomerBindVO vo);

    YhCustomerBind queryById(Integer id);

    YhCustomerBind offCustomerBind(Integer id);
}
