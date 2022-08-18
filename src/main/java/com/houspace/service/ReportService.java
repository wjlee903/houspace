package com.houspace.service;

import com.houspace.dto.MediumPageRequestDTO;
import com.houspace.dto.PageRequestDTO;
import com.houspace.dto.PageResultDTO;
import com.houspace.dto.ReportDTO;
import com.houspace.entity.ReportEntity;


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

public interface ReportService {

    Long register(ReportDTO dto);

    PageResultDTO<ReportDTO, ReportEntity> getList(MediumPageRequestDTO requestDTO);

    ReportDTO read(Long gno);

    void modify(ReportDTO dto);

    void remove(Long gno);

    default ReportEntity dtoToEntity(ReportDTO dto) {
        ReportEntity entity = ReportEntity.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .category(dto.getCategory())
                .secret(dto.isSecret())
                .build();
        return entity;
    }

    default ReportDTO entityToDto(ReportEntity entity){

        ReportDTO dto  = ReportDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .category(entity.getCategory())
                .regdate(entity.getRegDate())
                .moddate(entity.getModDate())
                .secret(entity.isSecret())
                .build();

        return dto;
    }
}
