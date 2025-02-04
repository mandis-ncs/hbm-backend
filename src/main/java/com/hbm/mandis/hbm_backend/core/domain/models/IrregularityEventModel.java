package com.hbm.mandis.hbm_backend.core.domain.models;

import java.time.Instant;
import java.util.UUID;

public class IrregularityEventModel {
    private UUID id;
    private String deviceId;
    private Instant startTimestamp;
    private Instant endTimestamp;

    private IrregularityEventModel(Builder builder) {
        this.id = builder.id;
        this.deviceId = builder.deviceId;
        this.startTimestamp = builder.startTimestamp;
        this.endTimestamp = builder.endTimestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Instant getStartTimestamp() {
        return startTimestamp;
    }

    public Instant getEndTimestamp() {
        return endTimestamp;
    }

    public static final class Builder {
        private UUID id;
        private String deviceId;
        private Instant startTimestamp;
        private Instant endTimestamp;

        public Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder startTimestamp(Instant startTimestamp) {
            this.startTimestamp = startTimestamp;
            return this;
        }

        public Builder endTimestamp(Instant endTimestamp) {
            this.endTimestamp = endTimestamp;
            return this;
        }

        public IrregularityEventModel build() {
            return new IrregularityEventModel(this);
        }
    }
}