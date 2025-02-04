package com.hbm.mandis.hbm_backend.core.domain.models;

import java.time.Instant;

public class EcgMeasurementModel {
    private String id;
    private double value;
    private Instant timestamp;
    private String deviceId;
    private boolean anomaly;

    private EcgMeasurementModel(Builder builder) {
        this.id = builder.id;
        this.value = builder.value;
        this.timestamp = builder.timestamp;
        this.deviceId = builder.deviceId;
        this.anomaly = builder.anomaly;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public boolean isAnomaly() {
        return anomaly;
    }

    public static final class Builder {
        private String id;
        private double value;
        private Instant timestamp;
        private String deviceId;
        private boolean anomaly;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder value(double value) {
            this.value = value;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder anomaly(boolean anomaly) {
            this.anomaly = anomaly;
            return this;
        }

        public EcgMeasurementModel build() {
            return new EcgMeasurementModel(this);
        }
    }
}
