package com.example.apiserverex.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.apiserverex.dto.CommonMemberDTO;
import com.example.apiserverex.entity.CommonMember;
import com.example.apiserverex.repository.CommonMemberRepository;

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
