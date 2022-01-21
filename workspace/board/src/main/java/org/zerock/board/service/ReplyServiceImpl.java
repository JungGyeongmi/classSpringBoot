package org.zerock.board.service;

import org.springframework.stereotype.Service;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Reply;
import org.zerock.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);
        return reply.getRno();
    }
    
}
