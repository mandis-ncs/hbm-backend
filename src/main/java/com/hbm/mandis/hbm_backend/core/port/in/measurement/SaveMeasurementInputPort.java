package com.hbm.mandis.hbm_backend.core.port.in.measurement;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;

public interface SaveMeasurementInputPort {
    void save(EcgMeasurementModel ecgMeasurementModel);
}
