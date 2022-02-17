package org.zerock.bimovie3.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonMemberDTO {
  private Long mno;
  private String username;
  private String email;
  private String name;
  private String mobile;
  private String password;
  private boolean fromSocial;
  private LocalDateTime modDate;
  private LocalDateTime regDate;

  private Collection<String> roleSet;

}
