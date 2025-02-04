package com.hbm.mandis.hbm_backend.adapters.out.repository;

import com.hbm.mandis.hbm_backend.adapters.out.model.IrregularityEventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IrregularityEventEntityRepository extends MongoRepository<IrregularityEventEntity, UUID> {
}
