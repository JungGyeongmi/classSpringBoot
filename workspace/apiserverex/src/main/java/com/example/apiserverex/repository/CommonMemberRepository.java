package com.example.apiserverex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import com.example.apiserverex.entity.CommonMember;

public interface CommonMemberRepository extends JpaRepository<CommonMember, Long>{
  //EntityGraph일 경우
  //FETCH: entity graph에 명시한 attribute는 EAGER로 패치하고, 
  //나머지 attribute는 LAZY로 패치
  //LOAD: entity graph에 명시한 attribute는 EAGER로 패치하고, 
  //나머지 attribute는 entity에 명시한 fetch type이나 
  //디폴트 FetchType으로 패치
  @EntityGraph(attributePaths={"roleSet"},type=EntityGraphType.LOAD)
  @Query("select m from CommonMember m where m.fromSocial=:social "
          + "and m.email=:email")
  Optional<CommonMember> findByEmail(String email, boolean social);
  
  @EntityGraph(attributePaths={"roleSet"},type=EntityGraphType.LOAD)
  @Query("select m from CommonMember m where m.email=:email")
  Optional<CommonMember> findByEmail(String email);

}
