package guru.springframework.Springrestmvc.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private UUID id;
    private String customername;
    private Integer version;

    private LocalDateTime createddate;
    private LocalDateTime lastmodifieddate;



}