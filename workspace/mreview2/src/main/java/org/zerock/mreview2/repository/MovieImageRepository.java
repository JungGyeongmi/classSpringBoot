package org.zerock.mreview2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mreview2.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
    

}
