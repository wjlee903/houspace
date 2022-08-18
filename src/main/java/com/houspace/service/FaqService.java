package com.houspace.service;

import com.houspace.dto.FaqDTO;
import com.houspace.dto.PageResultDTO;
import com.houspace.dto.SamllPageRequestDTO;
import com.houspace.entity.FaqEntity;

/*
작성자 : 이용훈
내용 : 리스트 가져오기
작성일 : 22-07-01
 */

public interface FaqService {

    PageResultDTO<FaqDTO, FaqEntity> getListAll(SamllPageRequestDTO requestDTO);


    // Entity를 DTO로 변환
    default  FaqDTO entityToDto(FaqEntity entity) {
        FaqDTO dto = FaqDTO.builder()
                .fno(entity.getFno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();

        return dto;
    }
}
