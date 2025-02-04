package com.hbm.mandis.hbm_backend.core.port.out.persistence;

import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityEventModel;

import java.util.Optional;

public interface IrregularityEventPersistenceOutputPort {
    void save(IrregularityEventModel eventModel);
    Optional<IrregularityEventModel> getLastEvent(String deviceId);
}
