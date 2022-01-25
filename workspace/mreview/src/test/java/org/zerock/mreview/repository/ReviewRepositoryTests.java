package org.zerock.mreview.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void insertMovieRewviews() {
        IntStream.rangeClosed(1, 200).forEach(i->{
            Long mno = (long)(Math.random()*100)+1; // 영화의 넘버
            Long mid = ((long)(Math.random()*100)+1); // 리뷰어의 넘버

            Member member = Member.builder().mid(mid).build();
            Movie movie = Movie.builder().mno(mno).build();


            Review review = Review.builder()
            .member(member)
            .movie(movie)
            .grade((int)(Math.random()*5)+1)
            .text("이 영화의 감상 평은 .... " + i)
            .build();
            
            reviewRepository.save(review);
        });
    }
}
