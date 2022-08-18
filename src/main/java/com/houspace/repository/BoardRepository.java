package com.houspace.repository;

import com.houspace.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
 *  작성자 : 이원종
 *  작성일 : 22-06-27
 *  내용 : 메인 화면 리스트 불러오기
 *  내용 : 상세보기 화면 글 불러오기
 * */
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    /*
    @Query(value = " SELECT b.*, i.*, m.*, c.*, AVG(COALESCE(r.grade,0)), COUNT(r.review_num) " +
            "FROM board as b " +
            "LEFT OUTER JOIN image as i ON i.board_b_num = b.b_num " +
            "LEFT OUTER JOIN member as m ON m.email = b.member_email " +
            "LEFT OUTER JOIN category as c ON c.cg_num = b.category_cg_num " +
            "LEFT OUTER JOIN review as r ON r.board_b_num = b.b_num GROUP BY b.b_num ORDER BY b.b_num ",
            nativeQuery = true,
            countQuery = "select count(*) from board ")
     */
    /*
    @Query("select b, i, m, c, avg(coalesce(r.grade,0)),  count(distinct r) " +
            "from MemberEntity m " +
            "left outer join BoardEntity b on b.member = m " +
            "left outer join CategoryEntity c on c = b.category " +
            "left outer join ImageEntity i on i.board = b " +
            "left outer join ReviewEntity r on r.board = b " +
            " group by b ")
     */
    @Query("select b, i, m, c, avg(coalesce(r.grade,0)), count(distinct r) " +
            "from BoardEntity b " +
            "left outer join ImageEntity i on i.board = b " +
            "left outer join ReviewEntity r on r.board = b " +
            "join MemberEntity m on m = b.member " +
            "join CategoryEntity c on c = b.category group by b ")
    Page<Object[]> getListPage(Pageable pageable);


    /*
    @Query("select b, i ,avg(coalesce(r.grade,0)),  count(r)" +
            " from BoardEntity b left outer join ImageEntity i on i.board = b " +
            " left outer join ReviewEntity r on r.board = b " +
            " where b.bNum = :bNum group by i")
     */
    /*
    @Query(value = "SELECT b.*, i.*, m.*, c.*, avg(coalesce(r.grade, 0)), COUNT(r.review_num) from board b " +
            " left outer join image i ON i.board_b_num = b.b_num " +
            " LEFT OUTER JOIN member m ON m.email = b.member_email " +
            " LEFT OUTER JOIN category c ON c.cg_num = b.category_cg_num " +
            " left outer join review r ON r.board_b_num = b.b_num " +
            " WHERE b.b_num =:b_num group BY i.img_num",
            nativeQuery = true)
     */
    @Query("select b, i, m, c, avg(coalesce(r.grade,0)), count(r) " +
            "from BoardEntity b " +
            "left outer join ImageEntity i on i.board = b " +
            "left outer join ReviewEntity r on r.board = b " +
            "join MemberEntity m on m = b.member " +
            "join CategoryEntity c on c = b.category " +
            "where b.bNum =:bNum group by i ")
    List<Object[]> getBoardWithAll(Long bNum);

    /* 220705 추가 카테고리 ******************************************************/
    @Query("select b, i, m, c, avg(coalesce(r.grade,0)), count(distinct r) " +
            "from BoardEntity b " +
            "left outer join ImageEntity i on i.board = b " +
            "left outer join ReviewEntity r on r.board = b " +
            "join MemberEntity m on m = b.member " +
            "join CategoryEntity c on c = b.category " +
            "where b.category.cgNum =:cgNum group by b ")
    Page<Object[]> getCategoryListPage(Pageable pageable, Long cgNum);
    /* 220705 추가 카테고리 ******************************************************/






}