package com.cuellar.crudservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectDTO {

    private Long id;

    private String owner_UUID;

    private String name;

}
