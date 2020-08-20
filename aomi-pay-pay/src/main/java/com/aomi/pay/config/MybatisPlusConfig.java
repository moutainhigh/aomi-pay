package com.aomi.pay.config;

import com.aomi.pay.enums.DBTypeEnum;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <P>
 * Mybatis-Plus 配置
 * </p>
 * @author : hdq
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.aomi.pay.mapper.db*")
public class MybatisPlusConfig {

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		//paginationInterceptor.setLocalPage(true);
		return paginationInterceptor;
	}

	@Bean(name = "order")
	@ConfigurationProperties(prefix = "spring.datasource.druid.order")
	public DataSource order() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "user")
	@ConfigurationProperties(prefix = "spring.datasource.druid.user")
	public DataSource user() {
		return DruidDataSourceBuilder.create().build();
	}

	/**
	 * 动态数据源配置
	 *
	 * @return
	 */
	@Bean
	@Primary
	public DataSource multipleDataSource(@Qualifier("order") DataSource order,
										 @Qualifier("user") DataSource user) {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DBTypeEnum.order.getValue(), order);
		targetDataSources.put(DBTypeEnum.user.getValue(), user);
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.setDefaultTargetDataSource(user);
		return dynamicDataSource;
	}

	@Bean("sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(multipleDataSource(order(), user()));

		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setCacheEnabled(false);
		sqlSessionFactory.setConfiguration(configuration);
		//PerformanceInterceptor(),OptimisticLockerInterceptor()
		//添加分页功能
		sqlSessionFactory.setPlugins(new Interceptor[]{
				paginationInterceptor()
		});
//        sqlSessionFactory.setGlobalConfig(globalConfiguration());
		return sqlSessionFactory.getObject();
	}

}