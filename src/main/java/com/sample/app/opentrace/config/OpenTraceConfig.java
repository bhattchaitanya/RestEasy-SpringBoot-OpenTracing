package com.sample.app.configuration;

import com.wavefront.sdk.entities.tracing.sampling.Sampler;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties("opentrace")
public class OpenTraceConfig {

    private boolean enabled;

    private Source source;

    private SamplerConfig samplerConfig;

    private Target target;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public SamplerConfig getSamplerConfig() {
        return this.samplerConfig;
    }

    public void setSamplerConfig(SamplerConfig samplerConfig) {
        this.samplerConfig = samplerConfig;
    }

    public static class SamplerConfig {

        private boolean enabled;

        private SamplerScope samplerScope;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public SamplerScope getSamplerScope() {
            return samplerScope;
        }

        public void setSamplerScope(SamplerScope samplerScope) {
            this.samplerScope = samplerScope;
        }

        public static class SamplerScope {

            private RateSampler rateSampler;

            private DurationSampler durationSampler;

            public RateSampler getRateSampler() {
                return rateSampler;
            }

            public void setRateSampler(RateSampler rateSampler) {
                this.rateSampler = rateSampler;
            }

            public DurationSampler getDurationSampler() {
                return durationSampler;
            }

            public void setDurationSampler(DurationSampler durationSampler) {
                this.durationSampler = durationSampler;
            }

            public static class RateSampler {

                private boolean enabled;

                public long rate;

                public long getRate() {
                    return rate;
                }

                public void setRate(long rate) {
                    this.rate = rate;
                }

                public boolean isEnabled() {
                    return enabled;
                }

                public void setEnabled(boolean enabled) {
                    this.enabled = enabled;
                }

            }

            public static class DurationSampler {

                private boolean enabled;

                public long durationInMilliSeconds;

                public boolean isEnabled() {
                    return enabled;
                }

                public void setEnabled(boolean enabled) {
                    this.enabled = enabled;
                }

                public long getDurationInMilliSeconds() {
                    return durationInMilliSeconds;
                }

                public void setDurationInMilliSeconds(long durationInMilliSeconds) {
                    durationInMilliSeconds = durationInMilliSeconds;
                }

            }

        }

    }

    public static class Source {

        private String application;

        private String cluster;

        private String service;
        private String shard;
        private HashMap<String, String> customTags;

        public String getApplication() {
            return application;
        }

        public void setApplication(String application) {
            this.application = application;
        }

        public String getCluster() {
            return cluster;
        }

        public void setCluster(String cluster) {
            this.cluster = cluster;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getShard() {
            return shard;
        }

        public void setShard(String shard) {
            this.shard = shard;
        }

        public HashMap<String, String> getCustomTags() {
            return customTags;
        }

        public void setCustomTags(HashMap<String, String> customTags) {
            this.customTags = customTags;
        }
    }


    public static class Target {

        private String reportingMechanism;

        private String server;

        private String token;

        private String source;
        private Boolean reportTraces;

        public String getReportingMechanism() {
            return reportingMechanism;
        }

        public void setReportingMechanism(String reportingMechanism) {
            this.reportingMechanism = reportingMechanism;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Boolean getReportTraces() {
            return reportTraces;
        }

        public void setReportTraces(Boolean reportTraces) {
            this.reportTraces = reportTraces;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
