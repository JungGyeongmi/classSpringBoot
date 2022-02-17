package org.zerock.bimovie.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@ToString
@Table(name = "tbl_poster")
public class Poster {
    private Long ino;

    private String fname;
    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
