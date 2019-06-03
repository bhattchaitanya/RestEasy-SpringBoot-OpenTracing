package com.sample.app;

import com.sample.app.filter.CustomWavefrontFilter;
import com.wavefront.config.ReportingUtils;
import com.wavefront.config.WavefrontReportingConfig;

import com.wavefront.sdk.common.application.ApplicationTags;
import com.wavefront.sdk.jaxrs.server.WavefrontJaxrsDynamicFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * SpringBoot entry point application
 *
 * @author Fabio Carvalho (facarvalho@paypal.com or fabiocarvalho777@gmail.com)
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
