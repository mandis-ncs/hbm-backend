package com.hbm.mandis.hbm_backend.core.usecase.measurement;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetHistoryUsecaseTest {

    @Mock
    private MeasurementPersistenceOutputPort measurementPersistenceOutputPort;

    @InjectMocks
    private GetHistoryUsecase getHistoryUsecase;

    private final String DEVICE_ID = "HBM-12345";

    private List<EcgMeasurementModel> sampleMeasurements;

    @BeforeEach
    void setUp() {
        sampleMeasurements = Arrays.asList(
                EcgMeasurementModel.builder()
                        .value(1.0)
                        .timestamp(Instant.now().minus(Duration.ofDays(5)))
                        .deviceId(DEVICE_ID)
                        .build(),
                EcgMeasurementModel.builder()
                        .value(1.2)
                        .timestamp(Instant.now().minus(Duration.ofDays(10)))
                        .deviceId(DEVICE_ID)
                        .build()
        );
    }

    @Test
    void shouldReturnMeasurementsFromLast30Days() {
        Instant thirtyDaysAgo = Instant.now().minus(Duration.ofDays(30));
        when(measurementPersistenceOutputPort.getLast30DaysMeasurements(eq(DEVICE_ID), any(Instant.class)))
                .thenReturn(sampleMeasurements);

        List<EcgMeasurementModel> result = getHistoryUsecase.getAll(DEVICE_ID);

        assertEquals(2, result.size());
        assertEquals(sampleMeasurements, result);
        verify(measurementPersistenceOutputPort, times(1)).getLast30DaysMeasurements(eq(DEVICE_ID), any(Instant.class));
    }

    @Test
    void shouldReturnEmptyListWhenNoMeasurementsFound() {
        when(measurementPersistenceOutputPort.getLast30DaysMeasurements(eq(DEVICE_ID), any(Instant.class)))
                .thenReturn(List.of());

        List<EcgMeasurementModel> result = getHistoryUsecase.getAll(DEVICE_ID);

        assertEquals(0, result.size());
        verify(measurementPersistenceOutputPort, times(1)).getLast30DaysMeasurements(eq(DEVICE_ID), any(Instant.class));
    }

}