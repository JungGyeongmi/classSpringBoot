package org.zerock.club.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.club.dto.ClubMemberDTO;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.repository.ClubMemberRepository;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{
    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean loginCheck(String username, String password) {
        
        log.info("loginCheck.....");
        boolean passResult = false;
        String enPw = passwordEncoder.encode("1");
        passResult = passwordEncoder.matches(password, enPw);
        boolean usernameResult = username.equals("user1")?true:false;
        return passResult && usernameResult;
    }

    @Override
    public void modify(ClubAuthMemberDTO clubAuthMemberDTO) {
        Optional<ClubMember> result = clubMemberRepository.findById(clubAuthMemberDTO.getEmail());
        ClubMember clubMember = result.get();
        clubMember.changePassword(clubAuthMemberDTO.getPassword());
        clubMemberRepository.save(clubMember);
    }

    // @Override
    // public void modify(ClubMemberDTO clubAMemberDTO) {
    //     Optional<ClubMember> result = clubMemberRepository.findById(clubAMemberDTO.getEmail());
    //     ClubMember clubMember = result.get();
    //     clubMember.changePassword(clubAMemberDTO.getPassword());
        
    //     clubMemberRepository.save(clubMember);
    // }

}
