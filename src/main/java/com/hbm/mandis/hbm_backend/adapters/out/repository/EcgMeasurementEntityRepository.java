package com.hbm.mandis.hbm_backend.adapters.out.repository;

import com.hbm.mandis.hbm_backend.adapters.out.model.EcgMeasurementEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface EcgMeasurementEntityRepository extends MongoRepository<EcgMeasurementEntity, UUID> {

    @Query(value = "{ 'deviceId' : ?0 }", sort = "{ 'timestamp' : -1 }",
            fields = "{ '_id': 0, 'value': 1, 'timestamp': 1, 'deviceId': 1, 'anomaly': 1, 'irregularityType': 1 }")
    List<EcgMeasurementEntity> findTop60ByDeviceIdOrderByTimestampDesc(String deviceId, Pageable pageable);

    @Query(value = "{ 'deviceId' : ?0, 'timestamp' : { $gte: ?1 } }", sort = "{ 'timestamp' : -1 }")
    List<EcgMeasurementEntity> findMeasurementsByDeviceIdAndLast30Days(String deviceId, Instant thirtyDaysAgo);

    @Query(value = "{ 'timestamp' : { $lt: ?0 } }", delete = true)
    void deleteByTimestampBefore(Instant threshold);
}

