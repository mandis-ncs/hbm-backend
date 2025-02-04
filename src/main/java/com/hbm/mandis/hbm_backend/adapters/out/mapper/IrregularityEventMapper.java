package com.hbm.mandis.hbm_backend.adapters.out.mapper;

import com.hbm.mandis.hbm_backend.adapters.out.model.IrregularityEventEntity;
import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityEventModel;

public class IrregularityEventMapper {
    public static IrregularityEventEntity modelToEntity(IrregularityEventModel model) {
        if (model != null) {
            return IrregularityEventEntity.builder()
                    .id(model.getId())
                    .deviceId(model.getDeviceId())
                    .startTimestamp(model.getStartTimestamp())
                    .endTimestamp(model.getEndTimestamp())
                    .build();
        }
        return null;
    }

    public static IrregularityEventModel entityToModel(IrregularityEventEntity entity) {
        if (entity != null) {
            return IrregularityEventModel.builder()
                    .id(entity.getId())
                    .deviceId(entity.getDeviceId())
                    .startTimestamp(entity.getStartTimestamp())
                    .endTimestamp(entity.getEndTimestamp())
                    .build();
        }
        return null;
    }
}
