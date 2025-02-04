package com.hbm.mandis.hbm_backend.adapters.in.web.controllers;

import com.hbm.mandis.hbm_backend.adapters.in.web.controllers.dto.EcgMeasurementRequest;
import com.hbm.mandis.hbm_backend.adapters.out.mapper.MeasurementMapper;
import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.GetHistoryInputPort;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.SaveMeasurementInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    private final SaveMeasurementInputPort saveMeasurementInputPort;
    private final GetHistoryInputPort getHistoryInputPort;

    public MeasurementController(SaveMeasurementInputPort saveMeasurementInputPort,
                                 GetHistoryInputPort getHistoryInputPort) {
        this.saveMeasurementInputPort = saveMeasurementInputPort;
        this.getHistoryInputPort = getHistoryInputPort;
    }

    @Operation(summary = "Receive an ECG measurement")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Measurement successfully received and processed"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> receiveMeasurement(@RequestBody EcgMeasurementRequest measurementDto) {
        saveMeasurementInputPort.save(MeasurementMapper.requestToModel(measurementDto));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Retrieve the last 30 days of measurements for a given device")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved ECG measurement history",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EcgMeasurementModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Device ID not found",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping(value = "/history/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EcgMeasurementModel>> getLast30DaysMeasurements(@PathVariable String deviceId) {
        return ResponseEntity.ok().body(getHistoryInputPort.getAll(deviceId));
    }
}
