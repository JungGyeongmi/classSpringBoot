package org.zerock.bimovie.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.bimovie.entity.AuthorityRole;
import org.zerock.bimovie.entity.CommonMember;
import org.zerock.bimovie.repository.CommonMemberRepository;
import org.zerock.bimovie.security.dto.CommonAuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


// spring security에서 관리하는 class
@Log4j2
@Service
@RequiredArgsConstructor
public class CommonOAuth2UserDetailsService extends DefaultOAuth2UserService {
    
    private final CommonMemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    /*
      session에 담아주지 않고 그 값을 크롬에서 직접관리
      userRequest는 google로 부터 온 정보를 의미한다.
    */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CoommonOAuth2UserDetailsService.....userRequest: "+userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName"+clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("===============================");

        oAuth2User.getAttributes().forEach((k, v)-> {
            log.info(k+":"+v);
        });
        
        String email = null;
        if(clientName.equals("Google"))
            email = oAuth2User.getAttribute("email");
            log.info("email : "+email);
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
                member.getRoleSet().stream().map(role -> 
                new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toList()), oAuth2User.getAttributes());
                return cAuthMemberDTO;
            
    }

    private CommonMember saveSocialMember(String email){
        Optional<CommonMember> result = repository.findByEmail(email);
        if(result.isPresent()) return result.get();

        // 없다면
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
