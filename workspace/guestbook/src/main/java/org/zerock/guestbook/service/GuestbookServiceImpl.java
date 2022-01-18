package org.zerock.guestbook.service;

import java.util.Optional;
import java.util.function.Function;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor // 생성자를 만들때 자동으로 wired 시켜준다
public class GuestbookServiceImpl implements GuestbookService {

    // 상수화 시켜주지 않으면 순환참조 에러 발생
    // class a와 class b 가 있을때 서로 물리는 경우를 순환참조라고 하고
    // 이를 방지하기 위해서 내부에만 쓰이도록
    // 상수로 만들어 주는 것이다
    final private GuestbookRepository repository; 
    // Spring이 만들어주는 객체로 proxy객체라고 한다

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("register...DTO:" + dto);
        Guestbook entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);
        return entity.getGno();
    }

    @Override
    // 결과값을 결국 RageResultDTO로 받음
    public PageResultDTO<GuestbookDTO, Guestbook> getList (PageRequestDTO pageRequestDTO) {
        
        // 원하는 페이지의 번호와 갯수를 정렬과 함께 Pageable 초기화
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        // 검색조건을 위한 객체 생성
        BooleanBuilder builder = getSearch(pageRequestDTO);

        // 초기화된 Pageable과 repository를 통해서 결과를 담음
        Page<Guestbook> result = repository.findAll(builder, pageable);

        // 스트림에서 Page객체의 결과를 처리하기 위한 내용을 담은 함수
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));
        return new PageResultDTO<>(result, fn);
        
            // @Override
            // public GuestbookDTO apply(Guestbook entitty) {
            //     return entittyToDto(entity);
            // }
            // (entity->entityToDto(entity))
            // PageResultDTO의 생성자를 통해 list타입의 멤버변수에 결과를 담음
            // return new PageResultDTO<>(result, fn);
        }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();
        
        BooleanBuilder builder = new BooleanBuilder();
        
        QGuestbook qGuestbook = QGuestbook.guestbook;
        
        String keyword = pageRequestDTO.getKeyword();


        // gno > 0 조건만 생성
        BooleanExpression expression = qGuestbook.gno.gt(0L);
        builder.and(expression);
        
        // 검색 조건이 없는 경우
        if(type==null || type.trim().length()==0) {
            return builder;
        }

        // 검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        } 
        if(type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        } 
        
        if(type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        } 
        
        // 모든 조건 통합
        builder.and(conditionBuilder);

        return builder;
    }

    

    @Override
    public GuestbookDTO read (Long gno) {
        log.info("read.............");
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent()?entityToDTO(result.get()):null;
    }

    @Override
    public void modify(GuestbookDTO dto) {
        log.info("modify............"+dto.getGno());
        Optional<Guestbook> result = repository.findById(dto.getGno());
        if (result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeTitle(dto.getTitle());
            guestbook.changeContent(dto.getContent());
           repository.save(guestbook);
        } 
    }

    @Override
    public void remove(Long gno) {
        log.info("remove.........."+gno);
        repository.deleteById(gno);
    }
}

