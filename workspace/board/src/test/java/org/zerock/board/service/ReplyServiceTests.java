package org.zerock.board.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;

@SpringBootTest
public class ReplyServiceTests {
    
    @Autowired
    private ReplyService replyService;
    
    @Test
    void testGetList() {
        Long bno = 100L;
        List<ReplyDTO> replyDTOList = replyService.getList(bno);
        replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));
    }
}
