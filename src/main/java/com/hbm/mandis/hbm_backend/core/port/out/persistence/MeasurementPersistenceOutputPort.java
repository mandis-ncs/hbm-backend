package com.hbm.mandis.hbm_backend.core.port.out.persistence;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;

public interface MeasurementPersistenceOutputPort {
    void save(EcgMeasurementModel ecgMeasurementModel);
}
