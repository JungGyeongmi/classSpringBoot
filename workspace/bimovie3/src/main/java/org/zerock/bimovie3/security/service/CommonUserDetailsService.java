package org.zerock.bimovie3.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.bimovie3.entity.CommonMember;
import org.zerock.bimovie3.repository.CommonMemberRepository;
import org.zerock.bimovie3.security.dto.CommonAuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommonUserDetailsService implements UserDetailsService {
  
  private final CommonMemberRepository repository;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("BiUserDetailsService loadUserByUsername:"+username);
    Optional<CommonMember> result=repository.findByEmail(username);
    if(!result.isPresent()) {
      throw new UsernameNotFoundException("Check Email or Social");
    }
    CommonMember commonMember = result.get();
    log.info("commonMember: "+commonMember);
    CommonAuthMemberDTO cAuthMemberDTO = new CommonAuthMemberDTO(
      commonMember.getMno(),
      commonMember.getEmail(), commonMember.getPassword(), 
      commonMember.getName(), commonMember.getEmail(),
      commonMember.getMobile(),
      commonMember.isFromSocial(), 
      commonMember.getRoleSet().stream().map(
        role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
              .collect(Collectors.toSet())
    );
    cAuthMemberDTO.setName(commonMember.getName());
    cAuthMemberDTO.setFromSocial(commonMember.isFromSocial());
    return cAuthMemberDTO;
  }
}
