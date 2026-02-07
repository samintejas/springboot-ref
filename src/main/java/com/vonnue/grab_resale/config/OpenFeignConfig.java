package com.vonnue.grab_resale.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.vonnue.grab_resale")
public class OpenFeignConfig {
}
