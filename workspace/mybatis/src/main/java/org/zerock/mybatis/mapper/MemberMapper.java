package org.zerock.mybatis.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.zerock.mybatis.vo.MemberVO;

@Mapper
public interface MemberMapper {
/*
  VO는 지난시간에 제가 요요요 저저 말씀드린거있죠
  DTO는 view에서 컨트롤러로 넘오는 것이고
  VO가 받아서 dB에 넣고 오는데 그렇게 큰 의미는 없다
  entity가 db에 연결된 객체라면
  VO는 delivery정도로 생각하면 된다

  repository가 mapper로 변경되는 것이다
*/
   public List<MemberVO> getList();
  //  public Integer insert(MemberVO member); 

  /* 변수에는 #을 붙여준다 */
   @Select("Select * from m_member where mid=#{mid}")
   public MemberVO getMemberWithMid(Long mid);

   public void insert(MemberVO vo);
   public long update(MemberVO vo);
   public void delete(long mid);

  }
