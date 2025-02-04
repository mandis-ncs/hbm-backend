package com.hbm.mandis.hbm_backend.core.port.in.measurement;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;

import java.util.List;
import java.util.UUID;

public interface GetHistoryInputPort {
    List<EcgMeasurementModel> getAll(UUID deviceId);
}
