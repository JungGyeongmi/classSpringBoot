package org.zerock.bimovie.service;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.zerock.bimovie.dto.CommonMemberDTO;
import org.zerock.bimovie.entity.AuthorityRole;
import org.zerock.bimovie.entity.CommonMember;

public interface MemberService {
  void updateCommonMemberDTO(CommonMemberDTO cMemberDTO);

  default CommonMember dtoToEntity(CommonMemberDTO cMemberDTO) {
    CommonMember cMember = CommonMember.builder()
        .mno(cMemberDTO.getMno())
        .email(cMemberDTO.getEmail())
        .password(cMemberDTO.getPassword())
        .name(cMemberDTO.getName())
        .mobile(cMemberDTO.getMobile())
        .fromSocial(cMemberDTO.isFromSocial())
        // .roleSet(cMemberDTO.getAuthorities().stream().map(gran->{
        //   System.out.println("gran>>"+gran);
        //   return AuthorityRole.USER;
        // }).collect(Collectors.toSet()))
        .roleSet(cMemberDTO.getRoleSet().stream().map(
          new Function<String,AuthorityRole>() {
          @Override
          public AuthorityRole apply(String t) {
            if(t.equals("ROLE_USER")) return AuthorityRole.USER;
            else if(t.equals("ROLE_MEMBER")) return AuthorityRole.MEMBER;
            else if(t.equals("ROLE_ADMIN")) return AuthorityRole.ADMIN;
            else return AuthorityRole.USER;
          }
        }).collect(Collectors.toSet()))
        .build();
        
    return cMember;
  }

  default CommonMemberDTO entityToDTO(CommonMember cMember) {
    CommonMemberDTO cMemberDTO = CommonMemberDTO.builder()
        .mno(cMember.getMno())
        .email(cMember.getEmail())
        .name(cMember.getName())
        .mobile(cMember.getMobile())
        .fromSocial(cMember.isFromSocial())
        .roleSet(cMember.getRoleSet().stream().map( 
            role->new String("ROLE_"+role.name()))
            .collect(Collectors.toSet()))
        .regDate(cMember.getRegDate())
        .modDate(cMember.getModDate())
        .build();
    return cMemberDTO;
  }
}
