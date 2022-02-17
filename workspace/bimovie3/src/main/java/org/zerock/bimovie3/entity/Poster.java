package org.zerock.bimovie3.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "movie")
@Table(name= "tbl_poster")
public class Poster {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String fname;
    private int idx; // 포스터 순번

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    public void stetIdx(int dix){
        this.idx = idx;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
    }


}
