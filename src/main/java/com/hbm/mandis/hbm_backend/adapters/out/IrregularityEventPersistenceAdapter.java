package com.hbm.mandis.hbm_backend.adapters.out;

import com.hbm.mandis.hbm_backend.adapters.out.mapper.IrregularityEventMapper;
import com.hbm.mandis.hbm_backend.adapters.out.repository.IrregularityEventEntityRepository;
import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityEventModel;
import com.hbm.mandis.hbm_backend.core.port.out.persistence.IrregularityEventPersistenceOutputPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IrregularityEventPersistenceAdapter implements IrregularityEventPersistenceOutputPort {

    private final IrregularityEventEntityRepository eventEntityRepository;

    public IrregularityEventPersistenceAdapter(IrregularityEventEntityRepository eventEntityRepository) {
        this.eventEntityRepository = eventEntityRepository;
    }

    @Override
    public void save(IrregularityEventModel eventModel) {
        eventEntityRepository.save(IrregularityEventMapper.modelToEntity(eventModel));
    }

    @Override
    public Optional<IrregularityEventModel> getLastEvent(String deviceId) {
        return eventEntityRepository.findFirstByDeviceIdOrderByStartTimestampDesc(deviceId)
                .map(IrregularityEventMapper::entityToModel);
    }
}
