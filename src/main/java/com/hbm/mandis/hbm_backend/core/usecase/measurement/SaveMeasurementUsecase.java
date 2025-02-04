package com.hbm.mandis.hbm_backend.core.usecase.measurement;

import com.hbm.mandis.hbm_backend.core.domain.exceptions.MeasurementException;
import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityEventModel;
import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityType;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.SaveMeasurementInputPort;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.IrregularityEventPersistenceOutputPort;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class SaveMeasurementUsecase implements SaveMeasurementInputPort {
    // Definir baseline e limiar para detectar anomalias
    final double BASELINE_MIN = 0.8;
    final double BASELINE_MAX = 1.2;
    final double ANOMALY_THRESHOLD = 0.2; // 20% da baseline
    final double ANOMALY_MIN = BASELINE_MIN * (1 + ANOMALY_THRESHOLD);
    final double ANOMALY_MAX = BASELINE_MAX * (1 + ANOMALY_THRESHOLD);

    private static final Logger logger = LoggerFactory.getLogger(SaveMeasurementUsecase.class);

    private final MeasurementPersistenceOutputPort measurementPersistenceOutputPort;
    private final IrregularityEventPersistenceOutputPort eventPersistenceOutputPort;

    public SaveMeasurementUsecase(MeasurementPersistenceOutputPort measurementPersistenceOutputPort,
                                  IrregularityEventPersistenceOutputPort eventPersistenceOutputPort) {
        this.measurementPersistenceOutputPort = measurementPersistenceOutputPort;
        this.eventPersistenceOutputPort = eventPersistenceOutputPort;
    }

    @Override
    public EcgMeasurementModel save(EcgMeasurementModel ecgMeasurementModel) {

        // 1️⃣ Verificar se a medição é uma anomalia
        boolean isAnomaly = isAnomalous(ecgMeasurementModel.getValue(), BASELINE_MIN, BASELINE_MAX, ANOMALY_THRESHOLD);
        ecgMeasurementModel = ecgMeasurementModel.builder().anomaly(isAnomaly).build();

        // 2️⃣ Buscar histórico de medições
        List<EcgMeasurementModel> last60Measurements = measurementPersistenceOutputPort
                .getLast60Measurements(ecgMeasurementModel.getDeviceId());

        if (last60Measurements.isEmpty()) {
            logger.info("Ainda não há medições suficientes para análise. Salvando a primeira entrada.");
            return measurementPersistenceOutputPort.save(ecgMeasurementModel);
        }

        long anomalyCount = countAnomalies(last60Measurements);

        // 3️⃣ Processar regras de irregularidade
        Optional<IrregularityType> irregularityType = processIrregularity(ecgMeasurementModel, anomalyCount);

        if (isAnomaly && irregularityType.isPresent()) {
            ecgMeasurementModel = ecgMeasurementModel.builder()
                    .irregularityType(irregularityType.get())
                    .build();
        }

        try {
            // 4️⃣ Salvar a medição no banco
            logger.info("Saving Measurement: {}mV (Anomaly: {})", ecgMeasurementModel.getValue(), isAnomaly);
            return measurementPersistenceOutputPort.save(ecgMeasurementModel);

        } catch (Exception e) {
            throw new MeasurementException("Failed to save a new ECG measurement. Error: ", e);
        }
    }

    /**
     * Verifica se um valor de ECG está fora do limite de normalidade (anomalia).
     */
    private boolean isAnomalous(double value, double baselineMin, double baselineMax, double anomalyThreshold) {
        return value < (baselineMin * (1 - anomalyThreshold)) || value > (baselineMax * (1 + anomalyThreshold));
    }

    /**
     * Conta quantas anomalias existem dentro das últimas 60 medições.
     */
    private long countAnomalies(List<EcgMeasurementModel> measurements) {
        return measurements.stream().filter(EcgMeasurementModel::isAnomaly).count();
    }

    /**
     * Processa a irregularidade com base no histórico e cria eventos quando necessário.
     */
    private Optional<IrregularityType> processIrregularity(EcgMeasurementModel measurement, long anomalyCount) {
        String deviceId = measurement.getDeviceId();

        // Se houver pelo menos 5 anomalias em 60 medições, gerar um "bip"
        if (anomalyCount >= 5) {
            logger.warn("Detectadas {} anomalias nas últimas 60 medições. Criando evento BIP.", anomalyCount);
            createIrregularityEvent(deviceId, measurement, IrregularityType.BIP);
            return Optional.of(IrregularityType.BIP);
        }

        // Se houver 60 medições normais após um evento, gerar um "bipbip"
        Optional<IrregularityEventModel> lastEvent = eventPersistenceOutputPort.getLastEvent(deviceId);
        if (lastEvent.isPresent() && anomalyCount == 0) {
            logger.info("60 medições normais após uma irregularidade. Criando evento BIPBIP.");
            closeIrregularityEvent(lastEvent.get(), measurement);
            return Optional.of(IrregularityType.BIP_BIP);
        }

        return Optional.empty();
    }

    /**
     * Cria um evento de irregularidade no banco de dados.
     */
    private void createIrregularityEvent(String deviceId, EcgMeasurementModel measurement, IrregularityType type) {
        IrregularityEventModel event = IrregularityEventModel.builder()
                .deviceId(deviceId)
                .startTimestamp(measurement.getTimestamp())
                .ecgMeasurementModel(measurement)
                .build();

        eventPersistenceOutputPort.save(event);
    }

    /**
     * Fecha um evento de irregularidade registrando um timestamp de término.
     */
    private void closeIrregularityEvent(IrregularityEventModel event, EcgMeasurementModel measurement) {
        IrregularityEventModel updatedEvent = event.builder()
                .endTimestamp(measurement.getTimestamp())
                .build();

        eventPersistenceOutputPort.save(updatedEvent);
    }

}
