package com.example.apiserverex.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.example.apiserverex.entity.AuthorityRole;
import com.example.apiserverex.entity.CommonMember;
import com.example.apiserverex.repository.CommonMemberRepository;
import com.example.apiserverex.security.dto.CommonAuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommonOAuth2UserDetailsService extends DefaultOAuth2UserService{
  
  private final CommonMemberRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("CommonOAuth2UserDetailsService..userRequest:"+userRequest);
    String clientName = userRequest.getClientRegistration().getClientName();
    log.info("clientName:"+clientName);
    log.info("getParameters:"+userRequest.getAdditionalParameters());

    OAuth2User oAuth2User = super.loadUser(userRequest);
    log.info("===========================");
    oAuth2User.getAttributes().forEach((k,v)->{
      log.info(k+":"+v);
    });
    String email = null;
    if(clientName.equals("Google"))
      email = oAuth2User.getAttribute("email");
    log.info("email:"+email);
    CommonMember member = saveSocialMember(email);
    // return oAuth2User;
    CommonAuthMemberDTO cAuthMemberDTO = new CommonAuthMemberDTO(
      member.getMno(),
      member.getEmail(), 
      member.getPassword(), 
      member.getName(),
      member.getEmail(),
      member.getMobile(),
      member.isFromSocial(), 
      member
         .getRoleSet().stream()
         .map(role->new SimpleGrantedAuthority("ROLE_"+role.name()))
         .collect(Collectors.toList()),
      oAuth2User.getAttributes()
    );
    cAuthMemberDTO.setName(member.getName());
    return cAuthMemberDTO;
  }
  private CommonMember saveSocialMember(String email){
    Optional<CommonMember> result = repository.findByEmail(email, true);
    if(result.isPresent()) return result.get();

    //없다면
    CommonMember commonMember = CommonMember.builder()
    .email(email)
    .name(email)
    .password(passwordEncoder.encode("1"))
    .fromSocial(true)
    .build();
    commonMember.addMemberRole(AuthorityRole.USER);
    repository.save(commonMember);
    return commonMember;
  }
}
