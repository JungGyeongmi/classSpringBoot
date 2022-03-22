package org.zerock.mreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovie(Movie movie);

    // delete를 위해서
    @Modifying
    @Query("delete from Review r where r.movie.mno = :mno")
    void deleteByMno(Long mno);

}
