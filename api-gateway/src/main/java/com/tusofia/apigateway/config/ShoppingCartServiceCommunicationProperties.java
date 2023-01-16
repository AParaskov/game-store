package com.tusofia.apigateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "shopping-cart-service")
@EnableConfigurationProperties(value = ShoppingCartServiceCommunicationProperties.class)
public class ShoppingCartServiceCommunicationProperties {
    private String url;
}
