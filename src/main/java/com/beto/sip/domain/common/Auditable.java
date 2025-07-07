package com.beto.sip.domain.common;

import java.time.Instant;

public interface Auditable {
    Instant getCreatedAt();

    Long getCreatedBy();

    Instant getUpdatedAt();

    Long getUpdatedBy();

    String getStatus();
}
