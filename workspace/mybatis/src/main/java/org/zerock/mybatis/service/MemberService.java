package org.zerock.mybatis.service;

import java.util.List;

import org.zerock.mybatis.vo.MemberVo;

public interface MemberService {
    List<MemberVo> getList();
}
