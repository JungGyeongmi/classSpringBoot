package org.zerock.club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubMemberDTO {
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
}
