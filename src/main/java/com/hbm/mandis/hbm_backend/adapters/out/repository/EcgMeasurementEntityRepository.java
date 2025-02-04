package com.hbm.mandis.hbm_backend.adapters.out.repository;

import com.hbm.mandis.hbm_backend.adapters.out.model.EcgMeasurementEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EcgMeasurementEntityRepository extends MongoRepository<EcgMeasurementEntity, UUID> {
}
