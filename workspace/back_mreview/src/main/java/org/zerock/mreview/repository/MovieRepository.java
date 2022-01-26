package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
    
    // coalesce: 2개의 컬럼 중 존재하는 값으로 합치고 싶을 때
    // @Query("select max(mi), avg(coalesce(r.grade, 0)), count(distinct r) from Movie m " +
    // "left outer join MovieImage mi on mi.movie = m "+
    // "left outer join Review r on r.movie = m "+
    // "group by m ")
    // Page<Object[]> getLisPage(Pageable pageable);

    // 조금의 성능을 포기하고 서브쿼리를 사용하게 되면 max를 적용할 수 있다.
    @Query("select m,i,count(r) from Movie m left join MovieImage i "
    +"on m=i.movie and i.inum=(select max(i2.inum) from MovieImage i2 "
    +"where i2.movie = m ) " +
    "left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);

    // @Query("select m, i, count(r) from Movie m left join MovieImage i on i.movie = m " +
    //     " and i.inum = (select max(i2.inum) from MovieImage i2 where i2.movie = m) " +
    //     " left outer join Review r on r.movie = m group by m")
    // Page<Object[]> getListPage(Pageable pageable);
}