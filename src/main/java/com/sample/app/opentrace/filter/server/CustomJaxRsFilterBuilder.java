package com.sample.app.opentrace.filter.server;

import com.wavefront.internal.reporter.SdkReporter;
import com.wavefront.sdk.common.application.ApplicationTags;
import com.wavefront.sdk.jaxrs.Constants;
import com.wavefront.sdk.jaxrs.server.WavefrontJaxrsServerFilter;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;

public class CustomJaxRsFilterBuilder extends WavefrontJaxrsServerFilter {

    private final Tracer tracer;

    public CustomJaxRsFilterBuilder(SdkReporter wfJaxrsReporter, ApplicationTags applicationTags, @Nullable Tracer tracer, Set<String> headerTags) {
        super(wfJaxrsReporter, applicationTags, tracer, headerTags);
        this.tracer = tracer;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        super.filter(containerRequestContext);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
        if (this.tracer != null) {
            try {
                Scope scope = (Scope) containerRequestContext.getProperty(Constants.PROPERTY_NAME);
                if (scope != null) {
                    decorateSpan(scope.span());
                }
            } catch (ClassCastException var24) {
            }
        }

        super.filter(containerRequestContext, containerResponseContext);
    }


    public void decorateSpan(Span span) {
        MDC.getContext().forEach((key, value) -> {
            if (key != null || value != null) {
                if (value.getClass().equals(String.class) || key.getClass().equals(String.class)) {
                    if(((String)key).equalsIgnoreCase("intuit_context")){
                        Arrays.stream(((String)value).split(" "))
                                .forEach( k -> {
                                    String[] nameValuePair = k.split("=");
                                    if(nameValuePair.length == 2) span.setTag(nameValuePair[0], nameValuePair[1]);
                                });

                    }else {
                        if(((String)value).length() < 256) span.setTag((String)key, (String)value);
                    }
                }
            }
        });
    }
}
