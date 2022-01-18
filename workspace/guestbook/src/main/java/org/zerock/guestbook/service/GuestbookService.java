package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    // Entity -> DTO
    GuestbookDTO read(Long gno);
    void modify(GuestbookDTO dto);
    void remove(Long gno);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    // 여기 밑에 코드 확인 경로
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);
    default GuestbookDTO entityToDTO(Guestbook entity) {
        GuestbookDTO dto = GuestbookDTO.builder()
        .gno(entity.getGno())
        .title(entity.getTitle())
        .content(entity.getContent())
        .writer(entity.getWriter())
        .regDate(entity.getRegDate())
        .modDate(entity.getModDate())
        .build();
        return dto;
    }

}
