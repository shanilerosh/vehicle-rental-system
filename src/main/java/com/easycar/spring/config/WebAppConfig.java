package com.easycar.spring.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.easycar.spring")
@EnableWebMvc
public class WebAppConfig{

    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver canBeCalledAnything(){
        return new CommonsMultipartResolver();
    }
}
