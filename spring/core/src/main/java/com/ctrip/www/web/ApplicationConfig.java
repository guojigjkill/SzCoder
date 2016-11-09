package com.ctrip.www.web;

import com.ctrip.www.web.interceptor.ContextInterceptor;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by wang.na on 2016/11/8.
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {
    @Bean
    public HandlerInterceptor handlerInterceptor(){
        return new ContextInterceptor();
    }

    @Autowired
    HandlerInterceptor contextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contextInterceptor);
    }
}