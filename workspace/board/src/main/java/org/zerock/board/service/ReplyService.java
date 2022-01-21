package org.zerock.board.service;

import java.util.List;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

public interface ReplyService {

    // http 명령에 있는 프로토콜임 ??

    Long register(ReplyDTO replyDTO);

                    // 글번호에 따라서 들고와야하니까 bno
    List<ReplyDTO> getList(Long bno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
        .rno(replyDTO.getRno())
        .text(replyDTO.getText())
        .replyer(replyDTO.getReplyer())
        .board(board)
        .build();
        
        return reply;
    }


    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO replyDTO = ReplyDTO.builder()  
            .rno(reply.getRno())
            .text(reply.getText())
            .replyer(reply.getReplyer())
            .regDate(reply.getRegDate())
            .modDate(reply.getModDate())
        .build();

        return replyDTO;
    }
}
