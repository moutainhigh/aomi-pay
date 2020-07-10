package com.cloudbest.user.mapper;

import com.cloudbest.user.entity.CustomerAddr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户地址表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Repository
public interface CustomerAddrMapper extends BaseMapper<CustomerAddr> {


}
