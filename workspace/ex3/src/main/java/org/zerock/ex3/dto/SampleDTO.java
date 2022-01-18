package org.zerock.ex3.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SampleDTO {
    private Long sno;
    private String first, last;
    private LocalDateTime regTime;
}
