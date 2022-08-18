package com.houspace.service;

import com.houspace.dto.*;
import com.houspace.entity.*;

import java.util.List;
import java.util.stream.Collectors;

/*
작성자 : 이원종
내용 : 회원 데이터 확인을 위한 Entity, DTO
 */
public interface MemberService {

    //    회원가입
    /*
    void joinMember(MemberDTO memberDTO);
     */
    String joinMember(MemberDTO memberDTO);

    // dto 를 entity 객체로 변환하는 메서드
    // SpringBoot에서는 dto를 사용하지 않고 entity 객체로 CRUD를 해야한다.
    default MemberEntity dtoToEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.builder()
                .email(memberDTO.getEmail())
                .pw(memberDTO.getPw())
                .nickName(memberDTO.getNickName())
                .fromSocial(memberDTO.isFromSocial())
                .build();
        memberEntity.addMemberRole(MemberRole.USER);
        return memberEntity;
    }

    // View에 사용하기 위한 entity 객체를 dto로 변환
    default MemberDTO entityToDto(MemberEntity memberEntity) {
        MemberDTO memberDTO = MemberDTO.builder()
                .email(memberEntity.getEmail())
                .nickName(memberEntity.getNickName())
                .fromSocial(memberEntity.isFromSocial())
                .build();
        return memberDTO;
    }

    /* 추가 마이페이지 내가 예약한 글 가져오기 **********************************************/
    // mypage 내가 예약한 공간 가져오기
    PageResultDTO<BoardDTO, Object[]> getMyBookList(String email, PageRequestDTO pageRequestDTO);

    // mypage > 내 게시글 목록 (오채영 0701)
    PageResultDTO<BoardDTO, Object[]> getMyBoardList(String email, PageRequestDTO pageRequestDTO);

    // data를 읽기 위한 DTO 변환, ServiceImpl 에서 사용
    default BoardDTO entitiesToDTO(BoardEntity boardEntity, List<ImageEntity> images, MemberEntity memberEntity,
                                   BookInfoEntity bookInfoEntity, Double avg, Long reivewCnt) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bNum(boardEntity.getBNum())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .availableDate(boardEntity.getAvailableDate())
                .availableTime(boardEntity.getAvailableTime())
                .price(boardEntity.getPrice())
                .people(boardEntity.getPeople())
                .address(boardEntity.getAddress())
                .phoneNumber(boardEntity.getPhoneNumber())
                .regDate(boardEntity.getRegDate())
                .modDate(boardEntity.getModDate())
                .build();

        List<ImageDTO> imageDTOList = images.stream().map(image -> {
            return ImageDTO.builder().imgName(image.getImgName())
                    .path(image.getPath())
                    .uuid(image.getUuid())
                    .build();
        }).collect(Collectors.toList());
        boardDTO.setImageDTOList(imageDTOList);

        // 멤버(유저) 정보 가져오기
        MemberDTO memberDTO = MemberDTO.builder()
                .email(memberEntity.getEmail())
                .nickName(memberEntity.getNickName())
                .build();
        boardDTO.setEmail(memberDTO.getEmail());
        boardDTO.setNickName(memberDTO.getNickName());
        boardDTO.setFromSocial(memberDTO.isFromSocial());


        /* 예약정보 없을시 문제 수정 ***************************************/
        // 예약 정보 가져오기
        if (bookInfoEntity != null) {
            BookInfoDTO bookInfoDTO = BookInfoDTO.builder()
                    .bookNum(bookInfoEntity.getBookNum())
                    .bookClickDate(bookInfoEntity.getBookClickDate())
                    .build();
            boardDTO.setBookNum(bookInfoDTO.getBookNum());
            boardDTO.setBookClickDate(bookInfoDTO.getBookClickDate());
        } else {
            boardDTO.setBookNum(null);
            boardDTO.setBookClickDate(null);
        }
        /* 예약정보 없을시 문제 수정 ***************************************/

        boardDTO.setAvg(avg);
        boardDTO.setReviewCnt(reivewCnt.intValue());

        return boardDTO;
    }
    /* 추가 마이페이지 내가 예약한 글 가져오기 **********************************************/

    /* 2022.07.03. 추가 - 회원가입시 이메일 확인 ************************/
    String serachEmail(String email);
    /* 2022.07.03. 추가 - 회원가입시 이메일 확인 ************************/

    /* 2022.07.03. 추가 - 회원가입시 닉네임 확인 ************************/
    String serachNickName(String nickName);
    /* 2022.07.03. 추가 - 회원가입시 닉네임 확인 ************************/
}
