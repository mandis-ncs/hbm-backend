package com.hbm.mandis.hbm_backend.config.ports;

import com.hbm.mandis.hbm_backend.core.port.in.measurement.GetHistoryInputPort;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.SaveMeasurementInputPort;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.IrregularityEventPersistenceOutputPort;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;
import com.hbm.mandis.hbm_backend.core.usecase.measurement.GetHistoryUsecase;
import com.hbm.mandis.hbm_backend.core.usecase.measurement.SaveMeasurementUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PortInjection {

    @Primary
    @Bean
    public SaveMeasurementInputPort saveMeasurementInputPort(MeasurementPersistenceOutputPort measurementPersistenceOutputPort,
                                                             IrregularityEventPersistenceOutputPort eventPersistenceOutputPort) {
        return new SaveMeasurementUsecase(measurementPersistenceOutputPort, eventPersistenceOutputPort);
    }

    @Primary
    @Bean
    public GetHistoryInputPort getHistoryInputPort(MeasurementPersistenceOutputPort measurementPersistenceOutputPort) {
        return new GetHistoryUsecase(measurementPersistenceOutputPort);
    }
}
