package com.houspace.repository;

import com.houspace.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/*
	프로그램명 : 고객불만 및 개선사항 접수 게시판
	작성자 : 박유림
	최초작성일 : 2022-6-24
	마지막수정일 : 2022-6-29

	사용법 : 고객센터 게시판 버튼 클릭
	        -> 게시글 등록 (1.비밀글여부/2.건의사항구분/3.제목/4.내용/5.작성자)
	        -> 비밀글로 작성하면 타인 열람 불가 페이지로 이동됨
	        -> 비밀글 체크 해제하면 열람 가능
	        -> 수정은 구분/제목/내용만
	 => 수정/삭제는 작성자, 관리자만 가능하게 하는 것 구현 필요
	 => 비밀글 열람은 작성자, 관리자만 가능하게 하는 것 구현 필요
*/

public interface ReportRepository extends JpaRepository<ReportEntity, Long>, QuerydslPredicateExecutor<ReportEntity> {
}
