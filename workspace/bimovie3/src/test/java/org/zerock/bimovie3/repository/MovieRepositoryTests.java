package org.zerock.bimovie3.repository;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.bimovie3.entity.Movie;
import org.zerock.bimovie3.entity.Poster;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository repository;

    @Test
    public void testInsert() {
        log.info("test Insert......");
        Movie movie = Movie.builder().title("극한직업").build();

        movie.addPoster(Poster.builder().fname("극한직업포스터1.jpg").build());
        movie.addPoster(Poster.builder().fname("극한직업포스터2.jpg").build());
        movie.addPoster(Poster.builder().fname("극한직업포스터3.jpg").build());

        repository.save(movie);
        log.info(movie.getMno());
    }
    
   
    @Test
    @Transactional
    @Commit
    public void testUpdatePoster() {
        Movie movie = repository.getById(1L);
        movie.addPoster(Poster.builder().fname("극한직업포스터4.jpg").build());
        repository.save(movie);//upadate
    }

    @Test
    @Transactional
    @Commit
    public void testRemovePoster() {
        Movie movie = repository.getById(1L);
        movie.removePoster(2L);
        repository.save(movie);
    }


    // select하더라도 동시에 불러와야 한다면 transactional을 써야한다
    @Transactional
    @Test
    public void getPoster() {
        Movie movie = repository.getById(1L);
        Poster poster = movie.getPosterList().get(0);
        System.out.println("poster:"+poster);
    }

    @Test
    public void insertMovies() {
        IntStream.rangeClosed(10, 100).forEach(new IntConsumer() {
            @Override
            public void accept(int i){
                Movie movie = Movie.builder().title("세계명작"+i).build();
                movie.addPoster(Poster.builder().fname("세"+i+"포1.jpg").build());
                movie.addPoster(Poster.builder().fname("세"+i+"포2.jpg").build());
                repository.save(movie);
            }
        });
    }

    @Test
    @Transactional
    public void testPageing1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Movie> result = repository.findAll(pageable);
        result.getContent().forEach(new Consumer<Movie>() {
            @Override
            public void accept(Movie m) {
                log.info(m.getMno());
                log.info(m.getTitle());
                log.info(m.getPosterList().size());
                log.info("-------------");
            }
        }); 
    }

    @Test
    public void testPageing2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Movie> result = repository.findAll2(pageable);
        result.getContent().forEach(new Consumer<Movie>() {
            @Override
            public void accept(Movie m) {
                log.info(m.getMno());
                log.info(m.getTitle());
                log.info(m.getPosterList());
                log.info("-------------");
            }
        }); 
    }

    @Test
    public void testPageing3() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Object[]> result = repository.findAll3(pageable);
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
            log.info("-------------------");
        }); 
    }

    @Test
    public void testPageing4() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Object[]> result = repository.findAll3(pageable);
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
            log.info("-------------------");
        }); 
    }

}
