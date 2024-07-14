package com.atyaoh.content.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Mybatis-Plus 配置
 */
@Configuration
@MapperScan("com.atyaoh.content.mapper")
public class MybatisPlusConfig implements MetaObjectHandler {
    /**
     * 分页插件
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 添加时，自动添加创建时间
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createDate", LocalDateTime.now(), metaObject);
    }

    /**
     * 修改时，自动添加修改时间
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("changeDate", LocalDateTime.now(), metaObject);
    }
}