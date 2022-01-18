package org.zerock.guestbook.repository;

import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    GuestbookRepository repository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(new IntConsumer() {
            @Override
            public void accept(int i) {
                Guestbook guestbook = Guestbook.builder()
                        .title("Title...." + i)
                        .content("Content...." + i)
                        .writer("user" + (i % 10))
                        .build();
                System.out.println(repository.save(guestbook));
            }
        });
    }

    @Test
    public void updateTest() {
        // 없으면 exception이 뜬다
        Optional<Guestbook> result = repository.findById(300L);
        // 있다면
        if (result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeTitle("changed Title...");
            guestbook.changeContent("changed Content...");
            repository.save(guestbook);
        }
    }

    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno"));
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));
        Page<Guestbook> result = repository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}
