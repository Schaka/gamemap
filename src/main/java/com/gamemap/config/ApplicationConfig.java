package com.gamemap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ImportResource("classpath:applicationContext.xml")
@EnableScheduling
public class ApplicationConfig {

}
