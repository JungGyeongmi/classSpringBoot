package org.zerock.mreview2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview2.entity.Member;
import org.zerock.mreview2.entity.Movie;
import org.zerock.mreview2.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 메서드 쿼리 방식
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review mr where mr.member=:member ")
    void deleteByMember(Member member);
}
