package org.zerock.mreview2.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview2.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {


    // coalesce: 2개의 컬럼 중 존재하는 값으로 합치고 싶을 때
    @Query("select mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "group by m ")
    Page<Object[]> getListPage(Pageable pageable);

    // 조금의 성능을 포기하고 서브쿼리를 사용하게 되면 max를 적용할 수 있다.
    // @Query("select m,i,count(r) from Movie m left join MovieImage i "
    // +"on m=i.movie and i.inum=(select max(i2.inum) from MovieImage i2 "
    // +"where i2.movie = m ) " +
    // "left outer join Review r on r.movie = m group by m")
    // Page<Object[]> getListPage(Pageable pageable);

    // @Query("select m, i, count(r) from Movie m left join MovieImage i on i.movie
    // = m " +
    // " and i.inum = (select max(i2.inum) from MovieImage i2 where i2.movie = m) "
    // +
    // " left outer join Review r on r.movie = m group by m")
    // Page<Object[]> getListPage(Pageable pageable);

    // movie 번호를 통해서 해당되는 movie에 대한 정보(이미지,댓글,평점등)이 Object형태의 List로 담김
    @Query("select m, mi,avg(coalesce(r.grade,0)),count(r) " +
            "from Movie m left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "where m.mno =:mno group by mi")
    List<Object[]> getMovieWithAll(Long mno);

}