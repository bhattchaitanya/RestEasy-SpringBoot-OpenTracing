package com.sample.app.client;

import com.intuit.opentracing.wavefront.apache.httpclient.WavefrontHttpRequestInterceptor;
import com.intuit.opentracing.wavefront.apache.httpclient.WavefrontHttpResponseInterceptor;
import com.wavefront.sdk.jaxrs.client.WavefrontJaxrsClientFilter;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteClient {

    static final int CONNECTION_TIMEOUT = 20000;

    public <T> T createClient(Class<T> clazz, String baseurl, WavefrontJaxrsClientFilter filter) {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(CONNECTION_TIMEOUT)
                .build();

        CloseableHttpClient httpClientBuilder = null;
        httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnTotal(2000)
                .setMaxConnPerRoute(1000)
                .setDefaultRequestConfig(requestConfig)
                .build();
        ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClientBuilder, true);

        ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
        factory.registerProvider(ResteasyProviderFactory.class);

        ResteasyClientBuilder builder = new ResteasyClientBuilder().providerFactory(factory);
        builder.httpEngine(engine);

        ResteasyClient client = builder.build();

        client.register(filter);

        ResteasyWebTarget target = client.target(baseurl);
        return target.proxy(clazz);
    }


    public <T> T createClient(Class<T> clazz, String baseurl, WavefrontHttpRequestInterceptor wavefrontHttpRequestInterceptor, WavefrontHttpResponseInterceptor wavefrontHttpResponseInterceptor) {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(CONNECTION_TIMEOUT)
                .build();

        CloseableHttpClient httpClientBuilder = null;

        if (wavefrontHttpRequestInterceptor == null && wavefrontHttpResponseInterceptor == null) {
            httpClientBuilder = HttpClientBuilder.create()
                    .setMaxConnTotal(2000)
                    .setMaxConnPerRoute(1000)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
        } else {
            httpClientBuilder = HttpClientBuilder.create()
                    .setMaxConnTotal(2000)
                    .setMaxConnPerRoute(1000)
                    .setDefaultRequestConfig(requestConfig)
                    .addInterceptorFirst(wavefrontHttpRequestInterceptor)
                    .addInterceptorFirst(wavefrontHttpResponseInterceptor)
                    .build();
        }

        ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClientBuilder, true);

        ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
        factory.registerProvider(ResteasyProviderFactory.class);

        ResteasyClientBuilder builder = new ResteasyClientBuilder().providerFactory(factory);
        builder.httpEngine(engine);

        ResteasyClient client = builder.build();
        ResteasyWebTarget target = client.target(baseurl);
        return target.proxy(clazz);
    }

}
