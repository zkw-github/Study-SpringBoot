package com.student.zhaokangwei.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


//    /**
//     * 添加拦截器
//     * @param registry 注册处
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //1. 实例一下Token拦截器对象
//        HandlerInterceptor tokenHandlerInterceptor = new TokenInterceptor();
//        //2. 将拦截器注册到当前的registry，并为拦截器配置PathPattern
//        registry.addInterceptor(tokenHandlerInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login", "/zzz");
//    }


}