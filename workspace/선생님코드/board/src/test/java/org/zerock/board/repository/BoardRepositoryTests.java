package org.zerock.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

@SpringBootTest
public class BoardRepositoryTests {
  @Autowired
  BoardRepository repository;

  @Test
  public void insertBoard() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Member member = Member.builder()
          .email("user" + i + "@aaa.com").build();

      Board board = Board.builder()
          .title("Title..." + i).content("Content..." + i)
          .writer(member).build();
      repository.save(board);
    });
  }

  @Transactional
  @Test
  public void testRead1() {
    Optional<Board> result = repository.findById(100L);
    Board board = result.get();
    System.out.println(board);
    System.out.println(board.getWriter());
  }

  @Test
  public void testReadWriter() {
    Object result = repository.getBoardWithWriter(100L);
    Object[] arr = (Object[]) result;
    System.out.println(Arrays.toString(arr));
  }

  @Test
  public void testGetBoardWithReply() {
    List<Object[]> result = repository.getBoardWithReply(100L);
    for (Object[] arr : result)
      System.out.println(Arrays.toString(arr));
  }

  @Test
  public void testWithReplyCount() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("bno")
        .descending());
    Page<Object[]> result = repository.getBoardWithReplyCount(
        pageable);
    result.get().forEach(row -> {
      Object[] arr = (Object[]) row;
      System.out.println(Arrays.toString(arr));
    });
  }

  @Test
  public void testRead3() {
    Object result = repository.getBoardByBno(100L);
    Object[] arr = (Object[]) result;
    System.out.println(Arrays.toString(arr));
  }

}
