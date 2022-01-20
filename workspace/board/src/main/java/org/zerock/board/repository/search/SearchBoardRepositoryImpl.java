package org.zerock.board.repository.search;

import java.util.List;
import java.util.function.Consumer;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport
    implements SearchBoardRepository {
    
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
    log.info("search1........");
    
    // 1. 사용하고자 하는 Q 도메인을 선언
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;
    QMember member = QMember.member;
    
    // Querydsl을상속받아서 쓸 수 있는것 JPQL의 from
    // 2. JPQLQuery를 이용해서 서로 연관(JOIN) 시킴
    // from 불러들일 테이블
    JPQLQuery<Board> jpqlQuery = from(board);
    // 조인하고 조건 작성 (on) , 같을 경우
    // 
    jpqlQuery.leftJoin(member).on(board.writer.eq(member));
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

    // 3. 참조할 데이터를 선택함 (쿼리문)
    JPQLQuery<Tuple> tuple = jpqlQuery.select(
        board, member.email, reply.count());
    tuple.groupBy(board);


    log.info("--------------------------------");
    log.info(tuple);
    log.info("--------------------------------");

    
    List<Board> result = jpqlQuery.fetch();
    log.info(result);
    return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage.......");
        // 1. 사용하고자 하는 Q 도메인을 선언 (동적 쿼리 호출을 위해 선언)
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        // Querydsl을상속받아서 쓸 수 있는것 JPQL
        // 2. JPQLQuery를 이용해서 서로 연관(JOIN) 시킴
        // from 불러들일 테이블
        JPQLQuery<Board> jpqlQuery = from(board);
        // 조인하고 조건 작성 (on) , 같을 경우
        //
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        // 3. 참조할 데이터를 선택함 (쿼리문)
        JPQLQuery<Tuple> tuple = jpqlQuery.select(
                board, member, reply.count());

        // 4. 검색 조건을 위한 객체 선언
        BooleanBuilder builder = new BooleanBuilder();
        // 검색 조건 //gt : great than => 여기서는 0보다 크니까 전부
        BooleanExpression expression = board.bno.gt(0L);
        // 객체에 검색조건을 넣어줌
        builder.and(expression);

        if(type != null) {
            // 어떤 타입의 검색 종류가 올라올지 모르니까 배열을 넣는다
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for(String t : typeArr) {
                switch(t) {
                    //  type이 t인 경우 title에 keyword가 있는지 확인
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
        // if문의 결과를 가지고 tuple에 조건문을 설정
        tuple.where(builder);

        Sort sort = pageable.getSort(); // Sort를 메서드를 통해서 초기화
        sort.stream().forEach(new Consumer<Sort.Order>() {
          @Override
          public void accept(Sort.Order order) {
            Order direction = order.isAscending()?Order.ASC:Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
          }
        });
    

        // 게시물에 대한 정렬이기 때문에 // board의 목록에 따른 댓글 카운트
        tuple.groupBy(board);
        // Tuple을 List에 담음
        List<Tuple> result = tuple.fetch();
        // 찍어냄
        log.info(result);

        return null;
    }

}
