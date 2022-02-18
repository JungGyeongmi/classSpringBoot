package org.zerock.mybatis.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MemberVO {
    
    private Long mid;
    private String email;
    private String nickname;
    private String pw;

    private LocalDateTime regdate, moddate;
}
