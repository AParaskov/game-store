package com.tusofia.apigateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "product-service")
@EnableConfigurationProperties(value = ProductServiceCommunicationProperties.class)
public class ProductServiceCommunicationProperties {
    String url;
}
