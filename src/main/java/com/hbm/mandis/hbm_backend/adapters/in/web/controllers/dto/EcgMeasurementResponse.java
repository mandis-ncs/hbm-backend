package com.hbm.mandis.hbm_backend.adapters.in.web.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record EcgMeasurementResponse(
        double value,
        String deviceId,
        boolean isAnomaly,
        @JsonIgnore IrregularityType irregularityType
) {
}
