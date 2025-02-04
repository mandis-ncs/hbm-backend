package com.hbm.mandis.hbm_backend.adapters.out.model;

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
@Document(collection = "irregularities")
public class IrregularityEventEntity {
    @Id
    private UUID id;
    private String deviceId;
    private Instant startTimestamp;
    private Instant endTimestamp;
}
