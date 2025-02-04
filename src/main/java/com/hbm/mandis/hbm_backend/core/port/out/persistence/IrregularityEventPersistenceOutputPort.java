package com.hbm.mandis.hbm_backend.core.port.out.persistence;

import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityEventModel;

public interface IrregularityEventPersistenceOutputPort {
    void save(IrregularityEventModel eventModel);
}
