package com.hbm.mandis.hbm_backend.adapters.in.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {

    private final SaveMeasurementInputPort saveMeasurementInputPort;
    private final GetHistoryInputPort getHistoryInputPort;

    //TODO apply swagger
    @PostMapping
    public ResponseEntity<Void> receiveMeasurement(@RequestBody EcgMeasurementRequest measurementDto {
        saveMeasurementInputPort.save(MeasurementMapper.requestToModel(measurementDto));
        return ResponseEntity.ok();
    }

    @GetMapping("/history"))
    public ResponseEntity<List<EcgMeasurementResponse>> receiveMeasurement(@PathVariable String deviceId) {
        var models = getHistoryInputPort.get(deviceId);
        var response = MeasurementMapper.modelToResponse(models);
        return ResponseEntity.ok().body(response);
    }
}
