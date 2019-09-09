package com.sample.app.opentrace.utils;

import com.sample.app.opentrace.config.OpenTraceConfig;
import com.sample.app.opentrace.WavefrontTracer;
import com.wavefront.config.WavefrontReportingConfig;
import com.wavefront.sdk.common.application.ApplicationTags;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class OpenTraceUtils {
    private static final Logger logger = LogManager.getLogger(WavefrontTracer.class);

    @NotNull
    @Autowired
    OpenTraceConfig config;

    private ApplicationTags applicationTags;

    private WavefrontReportingConfig wavefrontReportingConfig;

    public ApplicationTags getApplicationTags() {
        return applicationTags;
    }

    public void setApplicationTags(ApplicationTags applicationTags) {
        this.applicationTags = applicationTags;
    }

    public WavefrontReportingConfig getWavefrontReportingConfig() {
        return wavefrontReportingConfig;
    }

    public void setWavefrontReportingConfig(WavefrontReportingConfig wavefrontReportingConfig) {
        this.wavefrontReportingConfig = wavefrontReportingConfig;
    }

    public ApplicationTags applicationTags() {
        /** App meta-data **/
        if (config != null && config.isEnabled() && config.getSource() != null && config.getTarget() != null) {

            String application = config.getSource().getApplication();
            String service = config.getSource().getService();
            String cluster = config.getSource().getCluster();
            String shard = config.getSource().getShard();
            ApplicationTags.Builder appTagsBuilder = new ApplicationTags.Builder(application, service);
            appTagsBuilder.cluster(cluster);
            appTagsBuilder.shard(shard);
            applicationTags = appTagsBuilder.build();
        }
        if (applicationTags == null) {
            logger.warn("Wavefront applicationtags object is null, since configurations could not be read from the source.");
        }
        return applicationTags;

    }


    public WavefrontReportingConfig wavefrontReportingConfig() {
        if (config != null && config.isEnabled() && config.getSource() != null && config.getTarget() != null) {
            /** Forwarder meta-data **/
            String reportingMechanism = config.getTarget().getReportingMechanism();

            String token_base64 = config.getTarget().getToken();
            if (StringUtils.isBlank(token_base64)) {
                logger.warn("Opentrace server token not found. Opentrace events will not be reported. Opentrace feature will be disabled.");
                return null;
            }

            String server = config.getTarget().getServer();
            String source = config.getTarget().getSource();
            boolean reportTraces = config.getTarget().getReportTraces();

            wavefrontReportingConfig = new WavefrontReportingConfig();
            wavefrontReportingConfig.setServer(server);
            wavefrontReportingConfig.setSource(source);
            wavefrontReportingConfig.setReportingMechanism(reportingMechanism);
            wavefrontReportingConfig.setReportTraces(reportTraces);
            wavefrontReportingConfig.setToken(new String(Base64.decodeBase64(token_base64)));

        } else {
            logger.warn("Unable to construct wavefrontReportingConfig object since configuration is either disabled or properties are not set.");
        }
        return wavefrontReportingConfig;
    }


}
