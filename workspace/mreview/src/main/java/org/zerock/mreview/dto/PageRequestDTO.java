package org.zerock.mreview.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
//page를 요청할 때 단순히 페이지 번호보다 객체로 처리 위함
public class PageRequestDTO {
  private int page;
  private int size;
  private String type;
  private String keyword;

  public PageRequestDTO() {
    this.page = 1;
    this.size = 10;
  }

  public Pageable getPageable(Sort sort) {
    return PageRequest.of(page - 1, size, sort);
  }

}
