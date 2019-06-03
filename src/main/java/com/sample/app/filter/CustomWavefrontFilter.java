package com.sample.app.filter;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.wavefront.config.ReportingUtils;
import com.wavefront.config.WavefrontReportingConfig;
import com.wavefront.opentracing.WavefrontTracer;
import com.wavefront.opentracing.reporting.WavefrontSpanReporter;
import com.wavefront.sdk.common.WavefrontSender;
import com.wavefront.sdk.common.application.ApplicationTags;
import com.wavefront.sdk.jaxrs.reporter.WavefrontJaxrsReporter;
import com.wavefront.sdk.jaxrs.server.WavefrontJaxrsServerFilter.Builder;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.BooleanUtils;


@Provider
public class CustomWavefrontFilter implements DynamicFeature {
    private Builder wfJaxrsFilterBuilder;
    private WavefrontJaxrsReporter wavefrontJaxrsReporter;
    private Tracer tracer;
    private static int isReportStarted = 0;

    public CustomWavefrontFilter(ApplicationTags applicationTags, WavefrontReportingConfig wavefrontReportingConfig) {
        String source = wavefrontReportingConfig.getSource();
        WavefrontSender wavefrontSender = ReportingUtils.constructWavefrontSender(wavefrontReportingConfig);
        this.wavefrontJaxrsReporter = (new com.wavefront.sdk.jaxrs.reporter.WavefrontJaxrsReporter.Builder(applicationTags)).withSource(source).build(wavefrontSender);
        this.wfJaxrsFilterBuilder = new Builder(this.wavefrontJaxrsReporter, applicationTags);
        if (BooleanUtils.isTrue(wavefrontReportingConfig.getReportTraces())) {
            WavefrontSpanReporter wfSpanReporter;
            wfSpanReporter = new WavefrontSpanReporter.Builder().withSource(source).build(wavefrontSender);
            this.tracer = new WavefrontTracer.Builder(wfSpanReporter, applicationTags).build();
            wfJaxrsFilterBuilder.withTracer(this.tracer);
            GlobalTracer.register(this.tracer);
        }

    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
            this.wavefrontJaxrsReporter.start();
            featureContext.register(this.wfJaxrsFilterBuilder.build());
            System.out.println("################ Executed once ######################");
    }

    public Tracer getTracer() {
        return this.tracer;
    }
}
