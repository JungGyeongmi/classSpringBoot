package org.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.guestbook.entity.Guestbook;

// 동적 query에 대응하기 위해한 repository
public interface GuestbookRepository extends
                JpaRepository<Guestbook, Long>, 
                QuerydslPredicateExecutor<Guestbook> {

}