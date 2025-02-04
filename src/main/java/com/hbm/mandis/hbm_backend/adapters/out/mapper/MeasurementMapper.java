package com.hbm.mandis.hbm_backend.adapters.out.mapper;

import com.hbm.mandis.hbm_backend.adapters.in.web.controllers.dto.EcgMeasurementRequest;
import com.hbm.mandis.hbm_backend.adapters.in.web.controllers.dto.EcgMeasurementResponse;
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
                    .irregularityType(model.getIrregularityType())
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
                    .irregularityType(entity.getIrregularityType())
                    .build();
        }
        return null;
    }

    public static EcgMeasurementResponse modelToResponse(EcgMeasurementModel model) {
        if (model != null) {
            return new EcgMeasurementResponse(model.getValue(),
                    model.getDeviceId(),
                    model.isAnomaly(),
                    model.getIrregularityType());
        }
        return null;
    }
}
