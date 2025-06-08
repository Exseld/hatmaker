package com.hat.maker;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class TestConfig {
    @TestConfiguration
    public class TestCorsConfig {

        @Bean
        public WebMvcConfigurer testCorsConfiguration() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
//                    registry.addMapping("/**")
//                            .allowedOrigins("*")
//                            .allowedMethods("*")
//                            .allowedHeaders("*")
//                            .allowCredentials(true)
//                            .maxAge(3600);
                }
            };
        }
    }
}
