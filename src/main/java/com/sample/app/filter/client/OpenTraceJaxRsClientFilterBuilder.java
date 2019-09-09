package com.sample.app.filter.client;

import com.sample.app.OpenTraceUtils;
import com.sample.app.opentrace.WavefrontTracer;
import com.wavefront.config.ReportingUtils;
import com.wavefront.config.WavefrontReportingConfig;
import com.wavefront.sdk.common.WavefrontSender;
import com.wavefront.sdk.common.application.ApplicationTags;
import com.wavefront.sdk.jaxrs.client.WavefrontJaxrsClientFilter;
import com.wavefront.sdk.jaxrs.reporter.WavefrontJaxrsReporter;
import io.opentracing.Tracer;
import io.opentracing.noop.NoopTracerFactory;
import io.opentracing.util.GlobalTracer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.Provider;

@Provider
@Component
public class OpenTraceClientFilterBuilder {

    private static final Logger logger = LogManager.getLogger(WavefrontTracer.class);

    private WavefrontJaxrsClientFilter wavefrontJaxrsClientFilter;
    private WavefrontJaxrsReporter wavefrontJaxrsReporter;
    private Tracer tracer;

    @Autowired
    WavefrontTracer wavefrontTracer;

    @Autowired
    OpenTraceUtils openTraceUtils;

    public WavefrontJaxrsClientFilter getWavefrontJaxrsClientFilter() {
        Tracer tracer = GlobalTracer.get();
        ApplicationTags applicationTags;
        WavefrontReportingConfig wavefrontReportingConfig;

        if (tracer == null) {
            tracer = NoopTracerFactory.create();
        }

        if(wavefrontTracer == null){
            logger.warn("Wavefront client filter could not be created because wavefrontTracer object is null.");
            return null;
        }

        applicationTags = openTraceUtils.applicationTags();

        if(applicationTags == null){
            logger.warn("Wavefront client filter could not be created because applicationTags object is null.");
            return null;
        }

        wavefrontReportingConfig = openTraceUtils.getWavefrontReportingConfig();

        if(wavefrontReportingConfig == null){
            logger.warn("Wavefront client filter could not be created because wavefrontReportingConfig object is null.");
            return null;
        }

        String source = wavefrontReportingConfig.getSource();
        WavefrontSender wavefrontSender = ReportingUtils.constructWavefrontSender(wavefrontReportingConfig);
        this.wavefrontJaxrsClientFilter = new WavefrontJaxrsClientFilter(wavefrontSender, applicationTags, source, tracer);
        return this.wavefrontJaxrsClientFilter;
    }
}
