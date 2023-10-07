package com.student.zhaokangwei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    /**
     * 获取 ApiInfo对象
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot学习接口文档")
                .description("该接口应用于SpringBoot学习使用，开发者：赵康卫")
                .version("1.0")
                .build();
    }

    /**
     * 配置 Docket 到Spring容器
     * 相当于：
     * <bean class="springfox.documentation.spring.web.plugins.Docket">
     * </bean>
     *
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())     //配置API接口信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.student.zhaokangwei.controller"))      //配置需要生成API接口的包路径
                .paths(PathSelectors.any())         //配置API接口路径，为所有路径
                .build();
    }

}