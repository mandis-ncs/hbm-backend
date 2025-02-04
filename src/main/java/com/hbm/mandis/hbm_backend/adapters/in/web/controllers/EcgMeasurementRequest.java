package com.hbm.mandis.hbm_backend.adapters.in.web.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record EcgMeasurementRequest(
        @NotNull @NotEmpty
        double value,
        @NotNull @NotEmpty
        Instant timestamp,
        @NotBlank
        String deviceId
) {
}
