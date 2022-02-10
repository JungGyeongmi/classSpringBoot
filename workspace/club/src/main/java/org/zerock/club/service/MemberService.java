package org.zerock.club.service;

// import org.zerock.club.dto.ClubMemberDTO;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

public interface MemberService {
    boolean loginCheck(String username, String password);
    
    public void modify(ClubAuthMemberDTO clubAuthMemberDTO);
    // public void modify(ClubMemberDTO clubAMemberDTO);
}
