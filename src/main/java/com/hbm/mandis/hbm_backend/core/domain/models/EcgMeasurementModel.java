package com.hbm.mandis.hbm_backend.core.domain.models;

import java.time.Instant;
import java.util.UUID;

public class EcgMeasurementModel {
    private UUID id;
    private double value;
    private Instant timestamp;
    private String deviceId;
    private boolean anomaly;
    private IrregularityType irregularityType;

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

    public UUID getId() {
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

    public IrregularityType getIrregularityType() {
        return irregularityType;
    }

    public static final class Builder {
        private UUID id;
        private double value;
        private Instant timestamp;
        private String deviceId;
        private boolean anomaly;
        private IrregularityType irregularityType;

        public Builder() {
        }

        public Builder id(UUID id) {
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

        public Builder irregularityType(IrregularityType irregularityType) {
            this.irregularityType = irregularityType;
            return this;
        }

        public EcgMeasurementModel build() {
            return new EcgMeasurementModel(this);
        }
    }
}
