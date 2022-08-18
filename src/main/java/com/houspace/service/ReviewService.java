package com.houspace.service;

import com.houspace.dto.ReviewDTO;
import com.houspace.entity.BoardEntity;
import com.houspace.entity.MemberEntity;
import com.houspace.entity.ReviewEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReviewService {

    List<ReviewDTO> getListOfBoardEntity(Long bNum); //리뷰 가지고 오기

    Long register(ReviewDTO boardReviewDTO); //리뷰 등록

    void modify(ReviewDTO boardReviewDTO); //리뷰 수정

    void remove(Long reviewNum); //리뷰 삭제


    //리뷰 등록 (DTO->Entity 변환)
    default ReviewEntity dtoToEntity(ReviewDTO boardReviewDTO){
        ReviewEntity boardReview = ReviewEntity.builder()
                .reviewNum(boardReviewDTO.getReviewNum())
                .board(BoardEntity.builder().bNum(boardReviewDTO.getBnum()).build())
                .member(MemberEntity.builder().email(boardReviewDTO.getEmail()).build())
                .grade(boardReviewDTO.getGrade())
                .text(boardReviewDTO.getText())
                .build();

        return boardReview;
    }

    //리뷰 수정, 삭제(Entity->DTO 변환)
    default ReviewDTO entityToDto(ReviewEntity boardReview){
        ReviewDTO boardReviewDTO = ReviewDTO.builder()
                .reviewNum(boardReview.getReviewNum())
                .bnum(boardReview.getBoard().getBNum())
                .email(boardReview.getMember().getEmail())
                .nickName(boardReview.getMember().getNickName())
                .grade(boardReview.getGrade())
                .text(boardReview.getText())
                .regDate(boardReview.getRegDate())
                .modDate(boardReview.getModDate())
                .build();

        return boardReviewDTO;
    }
}
