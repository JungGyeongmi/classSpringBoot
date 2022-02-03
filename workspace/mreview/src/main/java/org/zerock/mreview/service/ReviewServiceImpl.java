package org.zerock.mreview.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;
import org.zerock.mreview.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno){
        
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        
        return result.stream()
        .map(movieReview -> entityToDto(movieReview))
        .collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO movieReviewDTO) {
        Review movieReview = dtoToEntity(movieReviewDTO);

        reviewRepository.save(movieReview);

        return movieReview.getReviewnum();
    }


    @Override
    public void modify(ReviewDTO moReviewDTO) {
        // save는 insert도 되지만 update도 된다
        // 그래서 먼저 불러오고 save시켜줘야함
       
        Optional<Review> result = reviewRepository.findById(moReviewDTO.getReviewnum());

        if(result.isPresent()){

            Review review = result.get();
            review.changeGrade(moReviewDTO.getGrade());
            review.changeText(moReviewDTO.getText());
            
            reviewRepository.save(review);
        }
        
    }

    @Override
    public void remove(Long reviewnum){
        reviewRepository.deleteById(reviewnum);
    }

}
