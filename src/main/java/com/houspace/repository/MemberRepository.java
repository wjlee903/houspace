package com.houspace.repository;

import com.houspace.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/*
	프로그램명 : 구글 로그인
	작성자 : 오채영
	최초작성일 : 2022-6-24
	마지막수정일 : 2022-6-27

	사용법 : findByEmail => 기존 회원인지(DB에 저장되어 있는지) 확인

	이원종 : 일반 회원가입 및 로그인시 사용
*/


// CRUD 담당
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from MemberEntity m where m.fromSocial = :social and m.email =:email")
    Optional<MemberEntity> findByEmail(String email, boolean social);

    /* 추가 마이페이지 내가 예약한 글 가져오기 ********************************/
    @Query("select b, i, m, bi, avg(coalesce(r.grade,0)), count(r) " +
            "from BoardEntity b " +
            "left outer join MemberEntity m on m = b.member " +
            "left outer join ReviewEntity r on r.member = m " +
            "left outer join ImageEntity i on i.board = b " +
            "left outer join BookInfoEntity bi on bi.board = b " +
            "where bi.member.email =:email " +
            "group by bi")
    Page<Object[]> getMyBookList(String email, Pageable pageable);
    /* 추가 마이페이지 내가 예약한 글 가져오기 ********************************/

    /* 마이페이지 > 내 게시글 목록 (오채영 0701) ********************************/
    @Query("select b, i, m, bi, avg(coalesce(r.grade,0)), count(r) " +
            "from BoardEntity b " +
            "left outer join MemberEntity m on m = b.member " +
            "left outer join ReviewEntity r on r.member = m " +
            "left outer join ImageEntity i on i.board = b " +
            "left outer join BookInfoEntity bi on bi.member = m " +
            "where b.member.email =:email group by b ")
    Page<Object[]> getMyBoardList(String email, Pageable pageable);

    /* 마이페이지 > 내 게시글 목록 (오채영 0701) */


  /* 2022.07.03. 추가 - 회원가입시 이메일 확인 ************************/
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from MemberEntity m where m.email =:email")
    Optional<Object> searchByEmail(String email);
    /* 2022.07.03. 추가 - 회원가입시 이메일 확인 ************************/


    /* 2022.07.03. 추가 - 회원가입시 이메일 확인 ************************/
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from MemberEntity m where m.nickName =:nickName")
    Optional<Object> findByNickName(String nickName);
    /* 2022.07.03. 추가 - 회원가입시 이메일 확인 ************************/



}
