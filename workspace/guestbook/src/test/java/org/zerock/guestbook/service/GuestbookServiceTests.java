package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
public class GuestbookServiceTests {
    
    @Autowired
    GuestbookService service;

    @Test
    void testRegister() {
        GuestbookDTO dto = GuestbookDTO.builder()
        .title("Sample title")
        .content("Sample content")
        .writer("user0")
        .build();
        // 저장해줘야 db에 들어간다 잊지 않기 // GuestbookService의 register함수 확인
        System.out.println(service.register(dto));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> pageResultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: "+pageResultDTO.isPrev());
        System.out.println("NEXT: "+pageResultDTO.isNext());
        System.out.println("TOAL: "+pageResultDTO.getTotalPage());

        for(GuestbookDTO dto : pageResultDTO.getDtoList()) {
            System.out.println(dto);
        }

        System.out.println("----------------------------------------");
        pageResultDTO.getPageList().forEach(
            i->System.out.printf("%3d", i));
    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
        .page(1)
        .size(10)
        .type("tc") // 검색 조건 t, c, w, tc, tcw ...
        .keyword("한글") // 검색 키워드
        .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: "+resultDTO.getTotalPage());

        System.out.println("--------------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);

            System.out.println("============================================");
            resultDTO.getPageList().forEach(i -> System.out.println(i));
        }

        
    }
}