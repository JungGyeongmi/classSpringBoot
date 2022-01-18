package org.zerock.ex22.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.ex22.entity.Memo;

// JpaRepository는 join처럼 쓰이는데 해당 테이블에 한해서 다 들고오는 경우에 사용.
@SpringBootTest
public class MemoRepositoryTests {
    // 인스턴스 만들기
    @Autowired
    // entitiy를 다루기위한 객체
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        // System.out.println(memoRepository.getClass().getName());
        assertNotNull(memoRepository.getClass().getName());
    }

    // test insert
    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // 인스턴스가 생성됨.
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    // test 조회
    @Test
    public void testSelect() {
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test
    public void testSelectAll() {
        List<Memo> result = memoRepository.findAll();
        for (Memo memo : result) {
            System.out.println(memo);
        }
    }

    // test 수정
    @Test
    public void testUpdate() {
        // 100번을 찾아서
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        if (result.isPresent()) {
            Memo memo = result.get();
            // 수정 - private이기 때문에 getter와 setter(builder)가 있어야 함.
            memo.changeMemoText("Update Text");
            memoRepository.save(memo);
        }
    }

    // test 삭제
    @Test
    public void testDelete() {
        // 먼저 100번을 찾아서
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        // 삭제
        if (result.isPresent()) {
            memoRepository.deleteById(mno);
        }
    }

    // 페이징 / 정렬 처리하기

    // Pageable인터페이스: 페이지 처리에 필요한 정보를 전달하는 용도
    @Test
    public void testpageDefault() {
        // 인터페이스
        Pageable pageable = PageRequest.of(0, 10); // 0부터 9까지
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("---------------------------");
        System.out.println("total Pages: " + result.getTotalPages());
        System.out.println("Total Count: " + result.getTotalElements());
        System.out.println("Page Number: " + result.getNumber());
        System.out.println("Page Size: " + result.getSize());
        System.out.println("has next page?: " + result.hasNext());
        System.out.println("first page?: " + result.isFirst());
    }

    // 정렬조건 추가하기
    @Test
    public void testSort() {
        // 내림차순으로 해서 0부터 9까지
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    // 쿼리 메서드 List
    @Test
    public void testQueryMethods() {
        // 70~80사이 Mno를 내림차순으로 list에 담음.
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        // 향상된 for문(type은 Memo)
        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    // 쿼리 메서드 Page
    @Test
    public void testQueryMethodsWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    // @Query 어노테이션
    @Test
    public void testGetListDesc() {
        List<Memo> result = memoRepository.getListDesc();
        result.forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testUpdateMemoText() {
        System.out.println(
                memoRepository.updateMemoText(1L, "Update Text"));
        // 데이터가 있을 경우 업데이트 성공시 1
        // 데이터가 없을 경우 업데이트 실패시 0
    }

    @Test
    public void testUpdateMemoBean() {
        Memo memo = Memo.builder().mno(1L).memoText("Update 2").build();
        System.out.println(
                memoRepository.updateMemoBean(memo));
    }

    @Test
    public void testGetListWithQuery() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.getListWithQuery(80L, pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testGetListWithQueryObject() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Object[]> result = memoRepository.getListWithQueryObject(80L, pageable);

        result.get().forEach(new Consumer<Object[]>() {
            public void accept(Object[] t) {
                for (Object obj : t) {
                    System.out.println(obj);
                }
            };
        });
    }

    @Test
    public void testGetNativeResult() {
        List<Memo> result = memoRepository.getNativeResult();
        result.forEach(memo -> {
            System.out.println(memo);
        });
    }
}