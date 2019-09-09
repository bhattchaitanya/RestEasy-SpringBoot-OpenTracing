package com.sample.app.opentrace.filter.server;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;

@Component
@Provider
class DatadogFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        final Span span = GlobalTracer.get().activeSpan();
        if (span != null) {
            decorateSpan(span);
        }
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