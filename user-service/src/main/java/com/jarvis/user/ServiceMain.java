package com.jarvis.user;

import com.google.common.collect.Sets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Date;

/**
 * Created by jian on 18-3-9
 */
@EntityScan
@EnableSwagger2
@EnableJpaRepositories
@SpringBootApplication
public class ServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMain.class, args);
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jarvis.user.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Date.class, Long.class)
                .securitySchemes(Collections.singletonList(new ApiKey("apiKey", "Authorization", "header")))
                .apiInfo(apiInfo())
                .consumes(Sets.newHashSet("application/json;charset=UTF-8"))
                .produces(Sets.newHashSet("application/json;charset=UTF-8"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("inventory-rest")
                .description("jarvis inventory rest service")
                .version("v1.0")
                .build();
    }

}
