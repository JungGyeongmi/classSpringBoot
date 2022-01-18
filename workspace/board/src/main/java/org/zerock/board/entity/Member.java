package org.zerock.board.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@Getter
@ToString
public class Member {
    // autoAdditing이 안되니까 Generate를 못 쓴다    
    @Id
    private String email;
    private String password;
    private String name;
}
