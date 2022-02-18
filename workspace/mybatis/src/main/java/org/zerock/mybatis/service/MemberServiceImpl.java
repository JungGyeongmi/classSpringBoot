package org.zerock.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.mybatis.mapper.MemberMapper;
import org.zerock.mybatis.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    
    private final MemberMapper mapper;

    @Override
    public List<MemberVO> getList() {
        return mapper.getList();
    }

    @Override
    public MemberVO getMember(long mid) {
       return mapper.getMemberWithMid(mid);
    }
}
