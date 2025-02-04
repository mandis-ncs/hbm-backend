package com.hbm.mandis.hbm_backend.adapters.out;

import com.hbm.mandis.hbm_backend.adapters.out.mapper.MeasurementMapper;
import com.hbm.mandis.hbm_backend.adapters.out.model.EcgMeasurementEntity;
import com.hbm.mandis.hbm_backend.adapters.out.repository.EcgMeasurementEntityRepository;
import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.MeasurementPersistenceOutputPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Component
public class MeasurementPersistenceAdapter implements MeasurementPersistenceOutputPort {

    private final EcgMeasurementEntityRepository ecgMeasurementEntityRepository;

    public MeasurementPersistenceAdapter(EcgMeasurementEntityRepository ecgMeasurementEntityRepository) {
        this.ecgMeasurementEntityRepository = ecgMeasurementEntityRepository;
    }

    @Override
    public EcgMeasurementModel save(EcgMeasurementModel ecgMeasurementModel) {
        var entity = MeasurementMapper.modelToEntity(ecgMeasurementModel);
        return MeasurementMapper.entityToModel(ecgMeasurementEntityRepository.save(entity));
    }

    @Override
    public List<EcgMeasurementModel> getLast60Measurements(String deviceId) {
        Pageable pageable = PageRequest.of(0, 60);
        List<EcgMeasurementEntity> entities = ecgMeasurementEntityRepository
                .findTop60ByDeviceIdOrderByTimestampDesc(deviceId, pageable);
        return entities.stream().map(MeasurementMapper::entityToModel).toList();
    }
}
