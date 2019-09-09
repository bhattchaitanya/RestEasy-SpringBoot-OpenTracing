package com.sample.app.client;

import com.sample.app.filter.client.OpenTraceHttpClientInterceptorBuilder;
import com.sample.app.filter.client.OpenTraceJaxRsClientFilterBuilder;
import com.sample.app.opentrace.utils.WavefrontHttpRequestInterceptor;
import com.sample.app.opentrace.utils.WavefrontHttpResponseInterceptor;
import com.wavefront.sdk.jaxrs.client.WavefrontJaxrsClientFilter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.resteasy.client.*;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.jboss.resteasy.client.jaxrs.engines.factory.ApacheHttpClient4EngineFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;

@Component
public class JaxrsClient {


    public <T> T createClient(Class<T> clazz, WavefrontJaxrsClientFilter filter){

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000)
                .build();

        CloseableHttpClient httpClientBuilder = null;

        ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClientBuilder,true);

        ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
        factory.registerProvider(ResteasyProviderFactory.class);

        ResteasyClientBuilder builder = new ResteasyClientBuilder().providerFactory(factory);
        builder.httpEngine(engine);

        ResteasyClient client = builder.build();

        client.register(filter);

        ResteasyWebTarget target = client.target("http://localhost:8080/ius/sample-app/");
        return target.proxy(clazz);
    }


    public <T> T createClient(Class<T> clazz, WavefrontHttpRequestInterceptor wavefrontHttpRequestInterceptor, WavefrontHttpResponseInterceptor wavefrontHttpResponseInterceptor){

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000)
                .build();

        CloseableHttpClient httpClientBuilder = null;

        if(wavefrontHttpRequestInterceptor != null && wavefrontHttpResponseInterceptor != null){
            httpClientBuilder = HttpClientBuilder.create()
                    .setMaxConnTotal(2000)
                    .setMaxConnPerRoute(1000)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
        }else {
            httpClientBuilder = HttpClientBuilder.create()
                    .setMaxConnTotal(2000)
                    .setMaxConnPerRoute(1000)
                    .setDefaultRequestConfig(requestConfig)
                    .addInterceptorFirst(wavefrontHttpRequestInterceptor)
                    .addInterceptorFirst(wavefrontHttpResponseInterceptor)
                    .build();
        }

        ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClientBuilder,true);

        ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
        factory.registerProvider(ResteasyProviderFactory.class);

        ResteasyClientBuilder builder = new ResteasyClientBuilder().providerFactory(factory);
        builder.httpEngine(engine);

        ResteasyClient client = builder.build();
        ResteasyWebTarget target = client.target("http://localhost:8080/ius/sample-app/");
        return target.proxy(clazz);
    }

}
