package com.sample.app.opentrace;

import com.sample.app.opentrace.config.OpenTraceConfig;
import com.sample.app.opentrace.filter.server.CustomWavefrontFilter;
import com.sample.app.opentrace.utils.OpenTraceUtils;
import com.wavefront.sdk.entities.tracing.sampling.CompositeSampler;
import com.wavefront.sdk.entities.tracing.sampling.DurationSampler;
import com.wavefront.sdk.entities.tracing.sampling.RateSampler;
import com.wavefront.sdk.entities.tracing.sampling.Sampler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Component
public class WavefrontTracer {
    private static final Logger logger = LogManager.getLogger(WavefrontTracer.class);

    @NotNull
    @Autowired
    OpenTraceConfig config;

    @Autowired
    OpenTraceUtils openTraceUtils;

    private Sampler sampler;

    public Sampler getSampler() {

        if (config.getSamplerConfig() == null
                || !config.getSamplerConfig().isEnabled()) {
            return null;
        }

        OpenTraceConfig.SamplerConfig samplerConfig = config.getSamplerConfig();
        OpenTraceConfig.SamplerConfig.SamplerScope samplerProps = samplerConfig.getSamplerScope();

        long durationInMilliSeconds = 0;
        long rate = 1;

        if (samplerProps.getDurationSampler() != null) {
            durationInMilliSeconds = samplerProps.getDurationSampler().getDurationInMilliSeconds();
        }
        if (samplerProps.getRateSampler() != null) {
            rate = samplerProps.getRateSampler().getRate();
        }

        Sampler sampler = null;

        if (samplerProps.getRateSampler() != null
                && samplerProps.getRateSampler().isEnabled()
                && samplerProps.getDurationSampler() != null
                && ! samplerProps.getDurationSampler().isEnabled()) {

            RateSampler rateSampler = new RateSampler(rate);
            sampler = rateSampler;

        } else if (samplerProps.getRateSampler() != null
                && ! samplerProps.getRateSampler().isEnabled()
                && samplerProps.getDurationSampler() != null
                && samplerProps.getDurationSampler().isEnabled()) {

            DurationSampler durationSampler = new DurationSampler(durationInMilliSeconds);
            sampler = durationSampler;
        } else if (samplerProps.getRateSampler() != null
                && samplerProps.getRateSampler().isEnabled()
                && samplerProps.getDurationSampler() != null
                && samplerProps.getDurationSampler().isEnabled()) {

            DurationSampler var1 = new DurationSampler(durationInMilliSeconds);
            RateSampler var2 = new RateSampler(rate);
            CompositeSampler compositeSampler = new CompositeSampler(Arrays.asList(var1, var2));
            sampler = compositeSampler;
        }

        return sampler;
    }

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

        if (! config.isEnabled()) {
            logger.warn("Opentrace configuration is not enabled.");
            return null;
        }

        return new CustomWavefrontFilter(openTraceUtils.applicationTags(), openTraceUtils.wavefrontReportingConfig(), getSampler());
    }


}
