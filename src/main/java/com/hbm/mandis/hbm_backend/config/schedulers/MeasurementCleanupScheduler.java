package com.hbm.mandis.hbm_backend.config.schedulers;

import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class MeasurementCleanupScheduler {

    private final MeasurementPersistenceOutputPort persistenceOutputPort;

    public MeasurementCleanupScheduler(MeasurementPersistenceOutputPort persistenceOutputPort) {
        this.persistenceOutputPort = persistenceOutputPort;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupOldMeasurements() {
        Instant thirtyDaysAgo = Instant.now().minus(Duration.ofDays(30));
        persistenceOutputPort.deleteByTimestampBefore(thirtyDaysAgo);
    }
}
