package org.zerock.mybatis.service;

import java.util.List;

import org.zerock.mybatis.vo.MemberVO;

public interface MemberService {
    List<MemberVO> getList();
    
    MemberVO getMember(long mid);

}
