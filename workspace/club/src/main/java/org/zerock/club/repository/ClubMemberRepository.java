package org.zerock.club.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.zerock.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
    // EntityGraph
    // LOAD: entity graph에 명시한 attribute는 EAGER로 패치하고, 
    // 나머지 attribute는 entity에 명시한 fetch type이나 
    // 디폴트 FetchType으로 패치
    // FETCH: entity graph에 명시한 attribute는 EAGER로 패치하고, 나머지 attribute는 LAZY로 패치
    // Query : 객체이기 때문에 ClubMember의 대소문자는 구분해줘야한다
    @EntityGraph(attributePaths = {"roleSet"}, type= EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial = :social and m.email= :email")
    Optional<ClubMember> findByEmail(String email, boolean social);

}
