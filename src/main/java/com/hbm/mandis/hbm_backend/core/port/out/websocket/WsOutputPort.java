package com.hbm.mandis.hbm_backend.core.port.out.websocket;

import com.hbm.mandis.hbm_backend.core.domain.models.EcgMeasurementModel;

public interface WsOutputPort {
    void sendMessage(EcgMeasurementModel message);
}
