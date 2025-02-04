package com.hbm.mandis.hbm_backend.adapters.in.web.controllers;

import com.hbm.mandis.hbm_backend.adapters.out.mapper.MeasurementMapper;
import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.GetHistoryInputPort;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.SaveMeasurementInputPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {

    private final SaveMeasurementInputPort saveMeasurementInputPort;
    private final GetHistoryInputPort getHistoryInputPort;

    public MeasurementController(SaveMeasurementInputPort saveMeasurementInputPort,
                                 GetHistoryInputPort getHistoryInputPort) {
        this.saveMeasurementInputPort = saveMeasurementInputPort;
        this.getHistoryInputPort = getHistoryInputPort;
    }

    //TODO apply swagger
    //TODO mandar para o Websocket para aparecer no frontend?
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> receiveMeasurement(@RequestBody EcgMeasurementRequest measurementDto) {
        saveMeasurementInputPort.save(MeasurementMapper.requestToModel(measurementDto));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EcgMeasurementModel>> getLast30DaysMeasurements(@PathVariable String deviceId) {
        return ResponseEntity.ok().body(getHistoryInputPort.getAll(UUID.fromString(deviceId)));
    }
}
