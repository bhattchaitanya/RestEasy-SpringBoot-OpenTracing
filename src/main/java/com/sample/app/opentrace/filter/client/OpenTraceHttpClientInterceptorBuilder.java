package com.sample.app.opentrace.filter.client;

import com.intuit.opentracing.wavefront.apache.httpclient.WavefrontHttpRequestInterceptor;
import com.intuit.opentracing.wavefront.apache.httpclient.WavefrontHttpResponseInterceptor;
import com.sample.app.opentrace.WavefrontTracer;
import com.wavefront.config.WavefrontReportingConfig;
import com.wavefront.sdk.common.application.ApplicationTags;
import io.opentracing.Tracer;
import io.opentracing.noop.NoopTracerFactory;
import io.opentracing.util.GlobalTracer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenTraceHttpClientInterceptorBuilder {

    private static final Logger logger = LogManager.getLogger(WavefrontTracer.class);

    private Tracer tracer;

    public WavefrontHttpRequestInterceptor getWavefrontHttpRequestClientFilter() {
        Tracer tracer = GlobalTracer.get();
        ApplicationTags applicationTags;
        WavefrontReportingConfig wavefrontReportingConfig;

        if (tracer == null) {
            tracer = NoopTracerFactory.create();
        }

        WavefrontHttpRequestInterceptor wavefrontHttpRequestInterceptor = new WavefrontHttpRequestInterceptor(tracer);
        return wavefrontHttpRequestInterceptor;
    }

    public WavefrontHttpResponseInterceptor getWavefrontHttpResponseClientFilter() {
        Tracer tracer = GlobalTracer.get();
        ApplicationTags applicationTags;
        WavefrontReportingConfig wavefrontReportingConfig;

        if (tracer == null) {
            tracer = NoopTracerFactory.create();
        }
        WavefrontHttpResponseInterceptor wavefrontHttpResponseInterceptor = new WavefrontHttpResponseInterceptor(tracer);
        return wavefrontHttpResponseInterceptor;
    }
}
