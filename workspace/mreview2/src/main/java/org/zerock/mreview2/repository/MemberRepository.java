package org.zerock.mreview2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mreview2.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
    
}
