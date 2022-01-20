package org.zerock.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.search.SearchBoardRepository;

public interface BoardRepository extends JpaRepository<Board, Long>,
    SearchBoardRepository { // Long은 primarykey 타입
    
    
    @Query("select b, w from Board b left join b.writer w where b.bno=:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno=:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    // 게시판, 회원, 댓글 갯수를 Page<object[]>로 목록을 만들어줌
    @Query(value="select b, w, count(r) from Board b left join b.writer w left join Reply r on r.board = b group by b", countQuery ="select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    // 상세 페이지 내용
    @Query("select b, w, count(r) from Board b left join b.writer w left outer join Reply r on r.board = b where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
