package com.hbm.mandis.hbm_backend.adapters.out.mapper;

import com.hbm.mandis.hbm_backend.adapters.in.web.controllers.EcgMeasurementRequest;
import com.hbm.mandis.hbm_backend.adapters.out.model.EcgMeasurementEntity;
import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;

public class MeasurementMapper {
    public static EcgMeasurementModel requestToModel(EcgMeasurementRequest request) {
        return EcgMeasurementModel.builder()
                .value(request.value())
                .timestamp(request.timestamp())
                .deviceId(request.deviceId())
                .build();
    }

    public static EcgMeasurementEntity modelToEntity(EcgMeasurementModel model) {
        if(model != null) {
            return EcgMeasurementEntity.builder()
                    .id(model.getId())
                    .value(model.getValue())
                    .deviceId(model.getDeviceId())
                    .timestamp(model.getTimestamp())
                    .anomaly(model.isAnomaly())
                    .build();
        }
        return null;
    }

    public static EcgMeasurementModel entityToModel(EcgMeasurementEntity entity) {
        if (entity != null) {
            return EcgMeasurementModel.builder()
                    .id(entity.getId())
                    .value(entity.getValue())
                    .deviceId(entity.getDeviceId())
                    .timestamp(entity.getTimestamp())
                    .anomaly(entity.isAnomaly())
                    .build();
        }
        return null;
    }
}
