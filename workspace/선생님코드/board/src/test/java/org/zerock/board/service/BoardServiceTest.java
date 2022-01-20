package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTest {
  @Autowired
  private BoardService boardService;
  @Test
  void testRegister() {
    BoardDTO dto = BoardDTO.builder()
    .title("Test")
    .content("Test")
    .writerEmail("user55@aaa.com")
    .build();
    Long bno = boardService.register(dto);
    System.out.println("bno >> "+ bno);
  }

  @Test
  public void testList(){
    //검색하고자 하는 페이지의 번호와 개수를 지정 하는 객체
    PageRequestDTO pageRequestDTO = new PageRequestDTO();

    //페이지 검색의 결과와 pagination의 정보를 담아둔 객체
    PageResultDTO<BoardDTO, Object[]> result = 
        boardService.getList(pageRequestDTO);
    

    for(BoardDTO boardDTO : result.getDtoList())
      System.out.println(boardDTO);
  }

  @Test
  public void testGet(){
    Long bno = 2L;
    System.out.println(boardService.get(bno));
  }

  @Test
  public void testRemove(){
    boardService.removeWithReplies(1L);
  }
  
  @Test
  public void testModify(){
    BoardDTO dto = BoardDTO.builder()
            .bno(2L).title("제목 변경").content("내용변경").build();
    boardService.modify(dto);
  }
}
