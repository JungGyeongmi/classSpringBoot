package org.zerock.ex22.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ex22.entity.Memo;

// JpaRepository는 join처럼 쓰이는데 해당 테이블에 한해서 다 들고오는 경우에 사용.
// <class명, primary key>
// 인터페이스도 extends할 수 있음.
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // 쿼리 메서드
    // 원하는 목록을 담을 때 List를 가져옴.
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // 페이징처리를 한 객체를 Page에 담음. (Pageable을 쓴..)
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable Pageable);

    // @Query 어노테이션
    // @Query 파라미터가 없는 경우
    // 실제 Table이름을 쓰지않고 JPA가 쓰는 객체의 명을 쓰게된다
    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    // @Query 파라미터가 있는 경우 (=:이거 말하는 거)
    // 매개변수가 2개
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText=:memoText where m.mno=:mno ")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText=:#{#memo.memoText} where m.mno=:#{#memo.mno} ")
    int updateMemoBean(@Param("memo") Memo memo);

    @Query(value = "select m from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

    @Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where " +
            "m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

    @Query(value = "select * from tbl_memo where mno > 0", nativeQuery = true)
    List<Memo> getNativeResult();

}