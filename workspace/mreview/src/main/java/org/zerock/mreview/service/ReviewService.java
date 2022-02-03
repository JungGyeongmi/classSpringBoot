package org.zerock.mreview.service;

import java.util.List;

import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

public interface ReviewService {
    List<ReviewDTO> getListOfMovie(Long mno);
    Long register(ReviewDTO movieReviewDTO);
    void modify(ReviewDTO moReviewDTO);
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO mReviewDTO){
        Review movieReview = Review.builder()
        .reviewnum(mReviewDTO.getReviewnum())
        .movie(Movie.builder().mno(mReviewDTO.getMno()).build())
        .member(Member.builder().mid(mReviewDTO.getMid()).build())
        .grade(mReviewDTO.getGrade())
        .text(mReviewDTO.getText())
        .build();
        return movieReview;
    }

    default ReviewDTO entityToDto(Review mReview) {
        ReviewDTO mReviewDTO = ReviewDTO.builder()
        .reviewnum(mReview.getReviewnum())
        .mno(mReview.getMovie().getMno())
        .mid(mReview.getMember().getMid())
        .nickname(mReview.getMember().getNickname())
        .email(mReview.getMember().getEmail())
        .grade(mReview.getGrade())
        .text(mReview.getText())
        .regDate(mReview.getRegDate())
        .modDate(mReview.getModDate())
        .build();

        return mReviewDTO;
    }
}
