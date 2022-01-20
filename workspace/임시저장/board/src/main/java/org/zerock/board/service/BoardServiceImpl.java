package org.zerock.board.service;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository repository; // 순환 참조를 막기위해서 final을 붙임
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);
        Board board = dtoToEntity(dto);
        repository.save(board);

        return board.getBno();
    }


    // 게시판 목록에 필요한 자료를 가져옴
    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        // Object[] 배열을 board DTO로 변환해줌
        Function<Object[], BoardDTO> fn = (en-> entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));
        
        // 게시판에 필요한 자료(board, member, replyCount)를
        // Object[]에 담고 다시 Page에 담아서 목록을 만들어냄
        Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno")));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    @Transactional // all or nothing // 여기서 두 개 이상을 불러오기 때문에 사용
    @Override
    public void removeWithReplies(Long bno) { // 삭제 기능 구현, 트랜잭션 추가

        // 댓글 부터 삭제
        replyRepository.deleteByBno(bno);
        // 게시물 삭제
        repository.deleteById(bno);
        
    }
    
    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = repository.findById(boardDTO.getBno());
        if(result.isPresent()){
            Board board = result.get();
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
            repository.save(board);
        }
    }

}
