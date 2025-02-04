package com.hbm.mandis.hbm_backend.core.port.out.persistence;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;

import java.time.Instant;
import java.util.List;

public interface MeasurementPersistenceOutputPort {
    EcgMeasurementModel save(EcgMeasurementModel ecgMeasurementModel);
    List<EcgMeasurementModel> getLast60Measurements(String deviceId);
    List<EcgMeasurementModel> getLast30DaysMeasurements(String deviceId, Instant thirtyDaysAgo);
    void deleteByTimestampBefore(Instant thirtyDaysAgo);
}
