package org.zerock.bimovie.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.bimovie.entity.CommonMember;
import org.zerock.bimovie.repository.CommonMemberRepository;
import org.zerock.bimovie.security.dto.CommonAuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommonUserDetailsService  implements UserDetailsService {
    // UserDetailsService : JPA와 Spring Security를 연결하는 객체

    private final CommonMemberRepository repository;

    /* 
     loadUserByUsername: 
     매개변수 username을 가져와서 JPA로 가서 username이 있는지 여부를 확인하는 메서드
     리턴타입은 UserDetails (회원정보) 
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      log.info("UserDetailsService loadUserByUsername:"+username);
     
      Optional<CommonMember> result = repository.findByEmail(username);

      if(!result.isPresent()) {
        throw new UsernameNotFoundException("Check Email or Social");
      }
      CommonMember commonMember = result.get();
      log.info("commonMember: "+commonMember);

      CommonAuthMemberDTO cAuthMemberDTO = new CommonAuthMemberDTO(
        commonMember.getMno(),
        commonMember.getEmail(),
        commonMember.getName(),
        commonMember.getEmail(), // username
        commonMember.getPassword(),
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