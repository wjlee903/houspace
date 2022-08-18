package com.houspace.entity;

import lombok.*;

import javax.persistence.*;

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

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "report") //7.4 수정

public class ReportEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    private String category;     // 문의 카테고리

    private boolean secret;     // 비밀글 여부

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeCategory(String category){
        this.category = category;
    }

}
