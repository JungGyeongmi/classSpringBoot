package org.zerock.bimovie.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.bimovie.dto.CommonMemberDTO;
import org.zerock.bimovie.entity.CommonMember;
import org.zerock.bimovie.repository.CommonMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final CommonMemberRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void updateCommonMemberDTO(CommonMemberDTO cMemberDTO) {
    log.info("updateCommonMemberDTO..CommonMemberDTO:"+cMemberDTO);
    cMemberDTO.setPassword(passwordEncoder.encode(cMemberDTO.getPassword()));
    CommonMember cMember = dtoToEntity(cMemberDTO);
    repository.save(cMember);
    log.info("cMember>>"+cMember);
  }
  
}
