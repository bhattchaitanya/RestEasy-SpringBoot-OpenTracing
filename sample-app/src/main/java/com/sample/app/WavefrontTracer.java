package com.sample.app;

import com.sample.app.filter.CustomWavefrontFilter;
import com.wavefront.config.ReportingUtils;
import com.wavefront.config.WavefrontReportingConfig;
import com.wavefront.sdk.common.application.ApplicationTags;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.ws.rs.ext.Provider;

@Service
public class WavefrontTracer {

    @Bean
    public CustomWavefrontFilter wavefrontJaxrsFeatureBean() {
        System.out.println("######################################################I  N  V  O K E D####################################################################################");
        String applicationTagsYamlFile = "/Users/cbhatt1/git-temp/wavefront/pcf-beachshirts/ius.yml";
        String wfReportingConfigYamlFile = "/Users/cbhatt1/git-temp/wavefront/pcf-beachshirts/wf-reporting-config.yml";
        ApplicationTags applicationTags = applicationTags = ReportingUtils.constructApplicationTags(applicationTagsYamlFile);
        WavefrontReportingConfig wfReportingConfig = ReportingUtils.constructWavefrontReportingConfig(wfReportingConfigYamlFile);
        String source = wfReportingConfig.getSource();
        return new CustomWavefrontFilter(applicationTags, wfReportingConfig);
    }
}
