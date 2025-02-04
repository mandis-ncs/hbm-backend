package com.hbm.mandis.hbm_backend.core.port.out.persistence;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;

import java.util.List;

public interface MeasurementPersistenceOutputPort {
    EcgMeasurementModel save(EcgMeasurementModel ecgMeasurementModel);
    List<EcgMeasurementModel> getLast60Measurements(String deviceId);
}
