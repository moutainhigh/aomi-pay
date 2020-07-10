package com.cloudbest.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudbest.order.entity.MainEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Repository
public interface MainMapper extends BaseMapper<MainEntity> {

    //根据主订单号修改订单状态
    //public int unlockStore(@Param("skuId") Integer skuId, @Param("num") Integer num);
    @Update("update order_main set pay_status = #{status} where main_order_id =#{id}")
    public int upd(@Param("id") String id, @Param("status") Integer status);

    @Select("select * from order_main where user_id = #{id} and pay_status = #{status}")
    public List<MainEntity> selectAllStatus(@Param("id") Long id, @Param("status") Integer status);

}







































