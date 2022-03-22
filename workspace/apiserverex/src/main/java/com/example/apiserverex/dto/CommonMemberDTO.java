package com.example.apiserverex.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
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

  @Builder.Default
  private List<String> roleSet = new ArrayList<>();

}