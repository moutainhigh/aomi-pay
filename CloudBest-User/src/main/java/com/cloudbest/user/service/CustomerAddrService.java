package com.cloudbest.user.service;

import com.cloudbest.user.entity.CustomerAddr;
import com.cloudbest.user.vo.CustomerAddrVO;

import java.util.List;

/**
 * <p>
 * 用户地址表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */

public interface CustomerAddrService  {
    /**
     * 根据用户id查询用户地址
     */
    List<CustomerAddr> queryAddrs (Long customerId) throws Exception;

    /**
     * 添加用户地址
     * @param customerAddr
     * @return
     */
    void addAddr(String token,CustomerAddr customerAddr) throws Exception;

    /**
     * 修改默认地址
     */
    void updateDef(String token,Integer id) throws Exception;

    List<CustomerAddr> queryList(CustomerAddrVO vo);

    CustomerAddr queryById(Integer id);
    /**
     * 删除地址
     */
    void delateAddr(Integer id);
    /**
     * 修改地址
     */
    void updateAddr(String token,CustomerAddr customerAddr) throws Exception;
}
