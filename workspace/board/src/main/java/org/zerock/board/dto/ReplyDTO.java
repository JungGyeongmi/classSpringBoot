package org.zerock.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {

    private Long rno;
    private String text;
    private String replyer;
    private Long bno; // board를 가르키는 bno
    private LocalDateTime regDate, modDate;    

}
