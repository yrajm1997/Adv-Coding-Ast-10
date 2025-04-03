package com.mystore.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
@ComponentScan("com.mystore.app")
public class OASConfig {
}
