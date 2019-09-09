package com.sample.app.opentrace.filter.server;


import com.wavefront.config.ReportingUtils;
import com.wavefront.config.WavefrontReportingConfig;
import com.wavefront.opentracing.WavefrontTracer;
import com.wavefront.opentracing.reporting.WavefrontSpanReporter;
import com.wavefront.sdk.common.WavefrontSender;
import com.wavefront.sdk.common.application.ApplicationTags;
import com.wavefront.sdk.entities.tracing.sampling.Sampler;
import com.wavefront.sdk.jaxrs.reporter.WavefrontJaxrsReporter;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.BooleanUtils;

import java.util.Arrays;
import java.util.HashSet;


@Provider
public class CustomWavefrontFilter implements DynamicFeature {
    private CustomJaxRsFilterBuilder wfJaxrsFilterBuilder;
    private WavefrontJaxrsReporter wavefrontJaxrsReporter;
    private Tracer tracer;

    public CustomWavefrontFilter(ApplicationTags applicationTags, WavefrontReportingConfig wavefrontReportingConfig, Sampler sampler) {
        String source = wavefrontReportingConfig.getSource();
        WavefrontSender wavefrontSender = ReportingUtils.constructWavefrontSender(wavefrontReportingConfig);
        this.wavefrontJaxrsReporter = (new com.wavefront.sdk.jaxrs.reporter.WavefrontJaxrsReporter.Builder(applicationTags)).withSource(source).build(wavefrontSender);
        if (BooleanUtils.isTrue(wavefrontReportingConfig.getReportTraces())) {
            WavefrontSpanReporter wfSpanReporter;
            wfSpanReporter = new WavefrontSpanReporter.Builder().withSource(source).build(wavefrontSender);
            if(sampler != null){
                this.tracer = new WavefrontTracer.Builder(wfSpanReporter, applicationTags)
                        .withSampler(sampler)
                        .build();
            }else{
                this.tracer = new WavefrontTracer.Builder(wfSpanReporter, applicationTags)
                        .build();
            }
            GlobalTracer.register(this.tracer);
        }
        this.wfJaxrsFilterBuilder = new CustomJaxRsFilterBuilder(this.wavefrontJaxrsReporter, applicationTags, tracer, new HashSet<String>(Arrays.asList("intuit_tid","intuit_userid")));
        this.wavefrontJaxrsReporter.start();
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        featureContext.register(this.wfJaxrsFilterBuilder);
    }

    public Tracer getTracer() {
        return this.tracer;
    }
}
