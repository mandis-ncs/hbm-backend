package com.hbm.mandis.hbm_backend.adapters.out.model;

import com.hbm.mandis.hbm_backend.core.domain.models.IrregularityType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "measurements")
public class EcgMeasurementEntity {
    @Id
    private UUID id;
    private double value;
    private Instant timestamp;
    private String deviceId;
    private boolean anomaly;
    private IrregularityType irregularityType;
}
