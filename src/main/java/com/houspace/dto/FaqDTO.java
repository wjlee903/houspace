package com.houspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
작성자 : 이용훈
내용 : DTO 작성
작성일 : 22-07-01
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FaqDTO {

    private Long fno;
    private String title;
    private String content;

}