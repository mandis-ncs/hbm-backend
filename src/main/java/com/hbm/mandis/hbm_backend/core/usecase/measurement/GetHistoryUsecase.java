package com.hbm.mandis.hbm_backend.core.usecase.measurement;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.GetHistoryInputPort;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;

import java.util.List;
import java.util.UUID;

public class GetHistoryUsecase implements GetHistoryInputPort {
    private final MeasurementPersistenceOutputPort measurementPersistenceOutputPort;

    public GetHistoryUsecase(MeasurementPersistenceOutputPort measurementPersistenceOutputPort) {
        this.measurementPersistenceOutputPort = measurementPersistenceOutputPort;
    }

    @Override
    public List<EcgMeasurementModel> getAll(UUID deviceId) {
        return List.of();
    }
}
