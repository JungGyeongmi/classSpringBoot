package org.zerock.ex22.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import javax.persistence.*;

@Entity
// 이게 있어야 class를 인식하고 table로 만들어줌
@Table(name = "tbl_memo")
// class 이름이 tbl_memo로 만들어짐
@ToString
@Getter
// 이 애노테이션이 있으면 이 클래스 안의 문자열은 주소값이 아닌
// 문자열로 자동으로 변환해줌
@Builder
// Setter 대신 사용 -> 바꿀때는 Setter가 필요함 private이 붙었기 때문에
// 바로 instance를 생성할 수 있다
// setter의 경우에는 인스턴스가 있어야 사용할 수 있다
@AllArgsConstructor
@NoArgsConstructor

public class Memo {
    @Id
    // 전략이 generationtype을 identify하겠다 PK가 된다는 뜻
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    // column옆에 그 조건을 설정해줄 수 있다
    // orm은 클래스로 모든것을 할 수 있다 object realtaional mapping
    @Column(length = 200, nullable = false)
    private String memoText;

    public void changeMemoText(String txt) {
        this.memoText = txt;
    }

    public void updateMemoText(long mno, String string) {
    }

}