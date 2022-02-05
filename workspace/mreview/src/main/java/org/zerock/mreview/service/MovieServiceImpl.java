package org.zerock.mreview.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repository.MovieImageRepository;
import org.zerock.mreview.repository.MovieRepository;
import org.zerock.mreview.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;
  private final MovieImageRepository imageRepository;
  private final ReviewRepository reviewRepository;

  @Transactional
  @Override
  public Long register(MovieDTO movieDTO) {
    log.info("movie/register....");
    Map<String, Object> entityMap = dtoToEntity(movieDTO);
    Movie movie = (Movie) entityMap.get("movie");
    List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");
    movieRepository.save(movie);
    movieImageList.forEach(movieImage -> {
      imageRepository.save(movieImage);
    });
    return movie.getMno();
  }

  @Override
  public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {

    Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

    Page<Object[]> result = movieRepository.getListPage(pageable);

    Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
        (Movie) arr[0],
        (List<MovieImage>) (Arrays.asList((MovieImage) arr[1])),
        (Double) arr[2],
        (Long) arr[3]));
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public MovieDTO getMovie(Long mno) {
    List<Object[]> result  = movieRepository.getMovieWithAll(mno);

    Movie movie = (Movie) result.get(0)[0]; // Movie Entity는 가장 앞에 존재-모든 Row가 동일한 값

    List<MovieImage> movieImageList = new ArrayList<>(); // 영화의 이미지 개수만큼 MovieImage객체 필요

    result.forEach(arr -> {
      MovieImage movieImage = (MovieImage)arr[1];
      movieImageList.add(movieImage);
    });

    Double avg = (Double) result.get(0)[2]; //평균 평점 - 모든 Row가 동일한 값
    Long reviewCnt = (Long) result.get(0)[3]; //리뷰 개수 - 모든 Row가 동일한 값

    return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
  }

  @Override
  public void modify(MovieDTO movieDTO, MovieImageDTO imageDTO) {
    Optional<Movie> result = movieRepository.findById(movieDTO.getMno());
    
    log.info("세상에 여기에>>>"+imageDTO.getInum());

    if(result.isPresent()){
        Movie movie = result.get();
        movie.changeTitle(movieDTO.getTitle());
        movieRepository.save(movie);
    }
  }

  @Transactional
  @Override
  public void removeWithReviews(Long mno) {
    reviewRepository.deleteByMno(mno);
    imageRepository.deleteByMno(mno);
    movieRepository.deleteById(mno);
  }
}