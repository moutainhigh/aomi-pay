package com.cloudbest.user.service;


import com.cloudbest.common.domain.Result;
import com.cloudbest.user.entity.CustomerCollect;
import com.cloudbest.user.vo.CustomerAddrVO;
import com.cloudbest.user.vo.CustomerCollectVO;

import java.util.List;

/**
 * <p>
 * 会员收藏的商品 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */

public interface CustomerCollectService  {
    /**
     * 根据用户id查询用户收藏
     * @param  token
     * @return
     */
    List<Result> queryCollect(String token) throws Exception;
    /**
     * 添加用户收藏
     * @param customerCollect
     * @return
     */
    void addCollect(CustomerCollect customerCollect);

    List<CustomerCollect> queryList(CustomerCollectVO vo);

    CustomerCollect queryById(Integer id);
    /**
     * 删除用户收藏
     * @param id
     */
    void delateCollect(Integer id);
}
