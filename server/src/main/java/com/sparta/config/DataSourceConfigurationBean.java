package com.sparta.config;

import com.sparta.provider.load.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfigurationBean {

    private Map<String, Provider> providerDataSource = new HashMap<>();

    @Bean
    public Map<String, Provider> providerDataSource() {
        return this.providerDataSource;
    }
}
