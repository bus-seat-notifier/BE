package com.be.bus.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "빈자리알림이 API 명세서",
                description = "빈자리알림이 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {
}