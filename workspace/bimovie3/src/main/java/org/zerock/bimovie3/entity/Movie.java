package org.zerock.bimovie3.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "posterList")
@Table(name = "tbl_moive")
public class Movie extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mno;
  private String title;

  @Builder.Default
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie",
  cascade = CascadeType.ALL,
  orphanRemoval = true) // 참조가 끊긴 애를 지우기위한것 (밑에서 참조가 끊긴 poster)
  private List<Poster> posterList = new ArrayList();

  public void addPoster(Poster poster){
    poster.stetIdx(this.posterList.size());
    poster.setMovie(this);
    posterList.add(poster);
  }

  /* poster를 삭제해주기 위한 코드 */ 
  public void removePoster(Long ino) {
    Optional<Poster> result = posterList.stream().filter(
      new Predicate<Poster>() {
        public boolean test(Poster p){
          return p.getIno() == ino;
        }
      }
    ).findFirst();
    result.ifPresent(new Consumer<Poster>() {
      @Override
      public void accept(Poster p) {
        p.setMovie(null); // 기존 Movie 객체 유지를 위해서
        posterList.remove(p);
        /* 
          arrayList를 upadate할 경우에는 posterList.set(idx, "upadet element"); 
          p.ino로 idx값을 대체할 수 있다
        */
      }
    });
    changeIdx(); // 번호를 다시 인덱싱하는 작업
  }

  // 이미지 삭제 후 idx를 재 분배하는 함수
  private void changeIdx() {
    for(int i = 0; i <posterList.size(); i++) {
      posterList.get(i).stetIdx(i);
    }
  }
}
