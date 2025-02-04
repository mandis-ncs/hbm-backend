package com.hbm.mandis.hbm_backend.adapters.out;

import com.hbm.mandis.hbm_backend.adapters.out.mapper.MeasurementMapper;
import com.hbm.mandis.hbm_backend.adapters.out.repository.EcgMeasurementEntityRepository;
import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;
import org.springframework.stereotype.Component;

@Component
public class MeasurementPersistenceAdapter implements MeasurementPersistenceOutputPort {

    private final EcgMeasurementEntityRepository ecgMeasurementEntityRepository;

    public MeasurementPersistenceAdapter(EcgMeasurementEntityRepository ecgMeasurementEntityRepository) {
        this.ecgMeasurementEntityRepository = ecgMeasurementEntityRepository;
    }

    @Override
    public void save(EcgMeasurementModel ecgMeasurementModel) {
        ecgMeasurementEntityRepository.save(MeasurementMapper.modelToEntity(ecgMeasurementModel));
    }
}
