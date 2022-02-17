package org.zerock.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.mybatis.mapper.MemberMapper;
import org.zerock.mybatis.vo.MemberVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    
    private final MemberMapper mapper;

    @Override
    public List<MemberVo> getList() {
        return mapper.getList();
    }
}
