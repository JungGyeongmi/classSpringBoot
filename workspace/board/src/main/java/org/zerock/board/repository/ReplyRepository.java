package org.zerock.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

public interface ReplyRepository  extends JpaRepository<Reply, Long> {
    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno ")
    void deleteByBno(Long bno);

    // rno는 reply의 primary key
    List<Reply> getRepliesByBoardOrderByRno(Board board);
    
}
