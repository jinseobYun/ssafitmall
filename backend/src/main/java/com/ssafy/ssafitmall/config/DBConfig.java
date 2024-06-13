package com.ssafy.ssafitmall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.ssafy.ssafitmall.model.dao")
public class DBConfig {

}
