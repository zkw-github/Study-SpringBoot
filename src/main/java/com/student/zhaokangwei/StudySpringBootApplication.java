package com.student.zhaokangwei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //用于启动springboot项目的注解
@EnableAutoConfiguration()
@MapperScan(basePackages = "com.student.zhaokangwei.mapper")  //配置mapper扫描
public class StudySpringBootApplication {
    public static void main(String[] args) {
        /**
         * 在这里，我可以写一些项目初始化的代码
         */
        SpringApplication.run(StudySpringBootApplication.class, args);
    }

}
