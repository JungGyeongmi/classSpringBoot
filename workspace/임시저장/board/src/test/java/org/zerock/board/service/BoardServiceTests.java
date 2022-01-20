package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    BoardService boardService;

    // 두 테이블이 무결성을 유지해야 하니까 없는 걸 참조하면 에러남(참조무결성 깨져서)
    @Test
    void testRegister() {
        BoardDTO dto = BoardDTO.builder()
        .title("Test")
        .content("Content")
        .writerEmail("user55@aaa.com")
        .build();

        Long bno = boardService.register(dto);
        System.out.println("bno >> "+ bno);
    }

    @Test
    public void testList() {

        // 검색하고자 하는 페이지의 번호와 개수를 지정하는 객체
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        // 페이지 검색의 결과와 pagenation의 정보를 담아둔 객체
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {
        Long bno = 100L;
        System.out.println(boardService.get(bno));
    }

    @Test
    public void testRemove() {
        Long bno =1L;
        boardService.removeWithReplies(bno);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder().bno(2L).title("제목 변경합니다.").content("내용 변경합니다.").build();

        boardService.modify(boardDTO);
    }
}
