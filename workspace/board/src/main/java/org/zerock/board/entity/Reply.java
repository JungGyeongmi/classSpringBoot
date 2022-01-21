package org.zerock.board.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String text;
    private String replyer;

    /* Reply가 Board를 N:1로 참조
    fetch = FetchType.LAZY join해서 한 번에 불러오는 것이 아니라 
    참조되야 할 부분이 있으면 그때, 순차적으로 불러와진다*/
    @ManyToOne(fetch = FetchType.LAZY) 
    private Board board;
}