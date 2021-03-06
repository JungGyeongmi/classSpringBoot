package org.zerock.board.repository.search;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl 
    extends QuerydslRepositorySupport
    implements SearchBoardRepository {
    //JpaRepository의 부족한 부분은 바로 각 항목에 대한 
    //max, min 값을 구하는 Predicate query와 
    //다양한 update,delete를 하는 query들을 만들어주는 것이 불가능
    //그래서 QuerydslRepositorySupport을 사용

  public SearchBoardRepositoryImpl() {
    super(Board.class);
  }

  @Override
  public Board search1() {
    log.info("serch1...........");
    // 1. 사용하고자 하는 Q도메인을 선언
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;
    QMember member = QMember.member;

    // 2. JPQLQuery을 이용해서 서로 연관(조인) 시킴
    JPQLQuery<Board> jpqlQuery = from(board);
    jpqlQuery.leftJoin(member).on(board.writer.eq(member));
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

    // 3. 쿼리 대상(내용)을 정한다. Tuple은 Object[]과 같은 기능
    JPQLQuery<Tuple> tuple = jpqlQuery.select(
        board, member.email, reply.count());
    tuple.groupBy(board);

    log.info("----------------------------");
    log.info(tuple);
    log.info("----------------------------");

    List<Board> result = jpqlQuery.fetch();
    log.info(result);
    return null;
  }

  @Override
  public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
    log.info("searchPage.....");
    // 1. 사용하고자 하는 Q도메인을 선언(동적쿼리 호출을 위해 선언)
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;
    QMember member = QMember.member;

    // 2. JPQLQuery을 이용해서 서로 연관(조인) 시킴
    JPQLQuery<Board> jpqlQuery = from(board);
    jpqlQuery.leftJoin(member).on(board.writer.eq(member));
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

    // 3. 쿼리 대상(내용)을 정한다. Tuple은 Object[]과 같은 기능
    JPQLQuery<Tuple> tuple = jpqlQuery.select(
        board, member, reply.count());

    // 4. 검색 조건을 위한 객체 선언
    BooleanBuilder builder = new BooleanBuilder();
    BooleanExpression expression = board.bno.gt(0L);
    builder.and(expression);

    if (type != null) {
      String[] typeArr = type.split("");
      BooleanBuilder conditionBuilder = new BooleanBuilder();
      for (String t : typeArr) {
        switch (t) {
          case "t":
            conditionBuilder.or(board.title.contains(keyword));
            break;
          case "w":
            conditionBuilder.or(member.email.contains(keyword));
            break;
          case "c":
            conditionBuilder.or(board.content.contains(keyword));
            break;
        }
      }
      builder.and(conditionBuilder);
    }
    tuple.where(builder);
    
    Sort sort = pageable.getSort();
    sort.stream().forEach(new Consumer<Sort.Order>() {
      @Override
      public void accept(Sort.Order order) {
        Order direction = order.isAscending()?Order.ASC:Order.DESC;
        String prop = order.getProperty();
        log.info("prop>>"+prop);
        PathBuilder orderByExpression = new PathBuilder<>(
          Board.class,"board");
        tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
      }
    });

    tuple.groupBy(board); //board의 목록에 따른 그룹
    tuple.offset(pageable.getOffset());
    tuple.limit(pageable.getPageSize());

    List<Tuple> result = tuple.fetch();
    log.info(result);
    long count = tuple.fetchCount();
    log.info("COUNT: "+count);

    return new PageImpl<Object[]>(result.stream().map(t->t.toArray()).collect(Collectors.toList()),pageable,count);
  }
}