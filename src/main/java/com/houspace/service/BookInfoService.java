package com.houspace.service;

import com.houspace.dto.BookInfoDTO;
import com.houspace.entity.BoardEntity;
import com.houspace.entity.BookInfoEntity;
import com.houspace.entity.MemberEntity;

/*
작성자 : 이원종
작성일 : 2022.06.30.
내용 : 예약 메소드 정의
 */
public interface BookInfoService {

    // 예약 하기
    Long bookingBoard(BookInfoDTO bookInfoDTO);


    /* 예약 정보 Dto Entity 변환 ******************************************/
    default BookInfoEntity dtoToEntity(BookInfoDTO bookInfoDTO) {
        // board 글번호
        BoardEntity board = BoardEntity.builder()
                .bNum(bookInfoDTO.getBNum())
                .build();

        // 예약하는 회원
        MemberEntity member = MemberEntity.builder()
                .email(bookInfoDTO.getEmail())
                .build();

        BookInfoEntity bookInfoEntity = BookInfoEntity.builder()
                .bookNum(bookInfoDTO.getBookNum())
                .bookClickDate(bookInfoDTO.getBookClickDate())
                .board(board)
                .member(member)
                .build();
        return bookInfoEntity;
    }

    /* 예약 정보 Dto Entity 변환 ******************************************/

    /* 2022.07.01. 추가 ******************************************/
    void bookDelete(Long bookNum);
    /* 2022.07.01. 추가 ******************************************/


    /* 추가 0704 예약시 버튼 비활성화 ***************************/
    boolean getBook(Long bNum);
    /* 추가 0704 예약시 버튼 비활성화 ***************************/

    /* 추가 0707 내가 예약한 글에서만 리뷰버튼 보이기 *****************/
    String getBookEmail(Long bNum);
    /* 추가 0707 내가 예약한 글에서만 리뷰버튼 보이기 *****************/


}
