package com.hbm.mandis.hbm_backend.core.usecase.measurement;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.GetHistoryInputPort;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class GetHistoryUsecase implements GetHistoryInputPort {
    private final MeasurementPersistenceOutputPort measurementPersistenceOutputPort;

    public GetHistoryUsecase(MeasurementPersistenceOutputPort measurementPersistenceOutputPort) {
        this.measurementPersistenceOutputPort = measurementPersistenceOutputPort;
    }

    //TODO: Add pageable and filters
    @Override
    public List<EcgMeasurementModel> getAll(String deviceId) {
        Instant thirtyDaysAgo = Instant.now().minus(Duration.ofDays(30));
        return measurementPersistenceOutputPort.getLast30DaysMeasurements(deviceId, thirtyDaysAgo);
    }
}
