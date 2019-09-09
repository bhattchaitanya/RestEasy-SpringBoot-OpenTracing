package com.sample.app;

import com.sample.app.configuration.OpenTraceConfig;
import com.sample.app.filter.CustomWavefrontFilter;
import com.wavefront.config.WavefrontReportingConfig;
import com.wavefront.sdk.common.application.ApplicationTags;
import com.wavefront.sdk.jaxrs.client.WavefrontJaxrsClientFilter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class WavefrontTracer {
    private static final Logger logger = LogManager.getLogger(WavefrontTracer.class);


    @NotNull
    @Autowired
    OpenTraceConfig config;

    @Autowired
    com.sample.app.OpenTraceUtils openTraceUtils;



    @Bean
    public CustomWavefrontFilter wavefrontJaxrsFeatureBean() {


        if (openTraceUtils.wavefrontReportingConfig() == null) {
            logger.warn("wavefrontReportingConfig is null. Critical opentrace configurations are missing");
            logger.warn("Opentrace feature is in disabled mode.");
            return null;
        }

        if (openTraceUtils.applicationTags() == null) {
            logger.warn("applicationTags object is null. Critical opentrace configurations are missing");
            logger.warn("Opentrace feature is in disabled mode.");
            return null;
        }

        if (openTraceUtils.wavefrontReportingConfig().getReportTraces() == false) {
            logger.warn("Opentrace configuration is partially enabled. Set reportTraces=true to enable trace data");
        }



        return new CustomWavefrontFilter(openTraceUtils.applicationTags(), openTraceUtils.wavefrontReportingConfig());
    }






}
