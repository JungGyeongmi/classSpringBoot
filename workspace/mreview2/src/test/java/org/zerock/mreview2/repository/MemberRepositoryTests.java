package org.zerock.mreview2.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview2.entity.Member;

@SpringBootTest
public class MemberRepositoryTests {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
            .email("r"+i+"@zerock.org")
            .pw("1")
            .nickname("reviewer"+i)
            .build();

            memberRepository.save(member);
        });
    }

    @Test
    @Commit
    @Transactional
    public void testDeleteMember() {
        Long mid = 1L;
        Member member = Member.builder().mid(mid).build();
        
        // memberRepository.deleteById(mid);
        // reviewRepository.deleteByMember(member);

        // 순서 주의 (매핑 테이블 삭제 후 기본 테이블 삭제 )
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
