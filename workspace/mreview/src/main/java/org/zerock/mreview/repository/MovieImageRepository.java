package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mreview.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
    
    @Modifying
    @Query("delete from MovieImage mi where mi.movie.mno = :mno")
    void deleteByMno(@Param("mno") Long mno);

    @Modifying
    @Query("delete from MovieImage mi where mi.inum = :inum")
    void deleteByInum(@Param("inum") Long inum);
}
