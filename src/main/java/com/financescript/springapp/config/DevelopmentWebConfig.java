package com.financescript.springapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("development")
@Configuration
@EnableWebMvc
@EnableAsync
@EnableScheduling
public class DevelopmentWebConfig implements WebMvcConfigurer {

}
