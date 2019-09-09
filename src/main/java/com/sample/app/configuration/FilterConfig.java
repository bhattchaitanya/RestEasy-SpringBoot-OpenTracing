package com.sample.app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties("clientfilter")
@EnableConfigurationProperties
public class FilterConfig {

    private boolean apacheHttpClient;

    public boolean isApacheHttpClient() {
        return apacheHttpClient;
    }

    public void setApacheHttpClient(boolean apacheHttpClient) {
        this.apacheHttpClient = apacheHttpClient;
    }
}
