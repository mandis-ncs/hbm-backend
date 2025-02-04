package com.hbm.mandis.hbm_backend.core.usecase.measurement;

import com.hbm.mandis.hbm_backend.core.domain.exceptions.IrregularityEventException;
import com.hbm.mandis.hbm_backend.core.domain.exceptions.MeasurementException;
import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityEventModel;
import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityType;
import com.hbm.mandis.hbm_backend.core.port.in.measurement.SaveMeasurementInputPort;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.IrregularityEventPersistenceOutputPort;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;

public class SaveMeasurementUsecase implements SaveMeasurementInputPort {
    private final MeasurementPersistenceOutputPort measurementPersistenceOutputPort;
    private final IrregularityEventPersistenceOutputPort eventPersistenceOutputPort;

    public SaveMeasurementUsecase(MeasurementPersistenceOutputPort measurementPersistenceOutputPort,
                                  IrregularityEventPersistenceOutputPort eventPersistenceOutputPort) {
        this.measurementPersistenceOutputPort = measurementPersistenceOutputPort;
        this.eventPersistenceOutputPort = eventPersistenceOutputPort;
    }

    @Override
    public void save(EcgMeasurementModel ecgMeasurementModel) {
        try {
            measurementPersistenceOutputPort.save(ecgMeasurementModel);
        } catch (Error e) {
            throw new MeasurementException("Failed to save a new ecg measurement. Error: ", e);
        }

        //TODO add logic to verify if its a irregularity, se for retornar bips and bipbips?
        if (ecgMeasurementModel.isAnomaly()) {
            IrregularityEventModel eventModel = IrregularityEventModel.builder()
                    .deviceId(ecgMeasurementModel.getDeviceId())
                    .startTimestamp(ecgMeasurementModel.getTimestamp())
                    .build();
            eventPersistenceOutputPort.save(eventModel);

//            if (ecgMeasurementModel.getTimestamp() > 2)
//                throw new IrregularityEventException("Another irregularity was identified. Type: " + IrregularityType.BIP_BIP);
//            else throw new IrregularityEventException("Irregularity was identified. Type: " + IrregularityType.BIP);

        } else {
            //pegar ultimo evento irregular
            //verificar se tem um end timestamp ja
            //se nao, settar end timestamp
            //salvar
        }
    }
}
