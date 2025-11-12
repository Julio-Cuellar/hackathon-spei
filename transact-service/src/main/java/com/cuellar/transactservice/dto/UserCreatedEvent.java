package com.cuellar.transactservice.messaging;

import lombok.Data;
import java.time.Instant;

@Data
public class UserCreatedEvent {
    private String type;
    private Integer version;
    private Instant timestamp;
    private DataPayload data;

    @Data
    public static class DataPayload {
        private String userUUID;
        private String email;
        private String name;
    }
}
