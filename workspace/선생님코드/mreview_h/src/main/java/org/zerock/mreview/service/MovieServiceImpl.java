package org.zerock.mreview.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repository.MovieImageRepository;
import org.zerock.mreview.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieServiceImpl implements MovieService{

  private final MovieRepository movieRepository;
  private final MovieImageRepository imageRepository;

  @Transactional
  @Override
  public Long register(MovieDTO movieDTO) {
    log.info("movie/register....");
    Map<String, Object> entityMap = dtoToEntity(movieDTO);
    Movie movie = (Movie) entityMap.get("movie");
    List<MovieImage> movieImageList = 
            (List<MovieImage>) entityMap.get("imgList");
    movieRepository.save(movie);
    movieImageList.forEach(movieImage ->{
      imageRepository.save(movieImage);
    });
    return movie.getMno();
  }

  @Override
  public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    Pageable pageable = pageRequestDTO.getPageable(
      Sort.by("mno").descending());
    
    Page<Object[]> result = movieRepository.getListPage(pageable);
    Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
      (Movie)arr[0], 
      (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
      (Double)arr[2],(Long)arr[3]));
    return new PageResultDTO<>(result, fn);
  }
  
}
