package org.zerock.bimovie3.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.zerock.bimovie3.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // JpaRepository의 findAll은 LazyInitializationException 발생
    // @Transactional 붙여주면 해결됨

    // findAll2는 성능면에서 안 좋음.
    @EntityGraph(attributePaths = "posterList", type= EntityGraphType.LOAD)
    @Query("select m from Movie m")
    Page<Movie> findAll2(Pageable pageable);
    
    // findAll3는 성능면에서 비교적 좋음!
    @Query("select m, p, count(p) from Movie m left join Poster p on p.movie = m group by p.movie")
    Page<Object[]> findAll3(Pageable pageable);

    // findAll4는 포스터의 두 번째 이미지를 들고 올 수 있음
    @Query("select m, p, count(p) from Movie m left join Poster p on p.movie = m where p.idx = 1 group by p.movie")
    Page<Object[]> findAll4(Pageable pageable);
}
