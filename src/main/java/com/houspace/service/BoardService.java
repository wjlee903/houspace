package com.houspace.service;

import com.houspace.dto.*;
import com.houspace.entity.BoardEntity;
import com.houspace.entity.CategoryEntity;
import com.houspace.entity.ImageEntity;
import com.houspace.entity.MemberEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
작성자 : 이원종
내용 : 메소드 작성 및 Entity, DTO 변환
       ( 게시글, 이미지, 멤버, 카테고리, 평점, 리뷰수)
 */
public interface BoardService {

    Long write(BoardDTO boardDTO);

    // 메인 화면 페이지 가져오기
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 상세보기 글 가져오기
    BoardDTO getBoard(Long bNum);

    // DB 저장을 위한 entity 변환 게시글, 이미지
    default Map<String, Object> dtoToEntity(BoardDTO boardDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        CategoryEntity category = CategoryEntity.builder()
                .cgNum(boardDTO.getCgNum())
                .build();

        MemberEntity member = MemberEntity.builder()
                .email(boardDTO.getEmail())
                .build();

        BoardEntity boardEntity = BoardEntity.builder()
                .bNum(boardDTO.getBNum())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())

                // 0704 '주의사항' 칼럼 추가 (오채영)
                .notice(boardDTO.getNotice())

                .availableDate(boardDTO.getAvailableDate())
                .availableTime(boardDTO.getAvailableTime())
                .price(boardDTO.getPrice())
                .people(boardDTO.getPeople())
                .address(boardDTO.getAddress())
                .phoneNumber(boardDTO.getPhoneNumber())
                .category(category)
                .member(member)
                .build();
        entityMap.put("board", boardEntity);

        List<ImageDTO> imageDTOList = boardDTO.getImageDTOList();
        // ImageDTO 처리
        if (imageDTOList != null & imageDTOList.size() > 0) {
            List<ImageEntity> imageEntityList = imageDTOList.stream().map(imageDTO -> {
                ImageEntity imageEntity = ImageEntity.builder()
                        .path(imageDTO.getPath())
                        .imgName(imageDTO.getImgName())
                        .uuid(imageDTO.getUuid())
                        .board(boardEntity)
                        .build();
                return imageEntity;
            }).collect(Collectors.toList());
            entityMap.put("imgList", imageEntityList);

        }
        return entityMap;
    }

    // data를 읽기 위한 DTO 변환, ServiceImpl 에서 사용
    default BoardDTO entitiesToDTO(BoardEntity boardEntity, List<ImageEntity> images, MemberEntity memberEntity,
                                   CategoryEntity categoryEntity, Double avg, Long reivewCnt) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bNum(boardEntity.getBNum())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())

                // 0704 '주의사항' 칼럼 추가 (오채영)
                .notice(boardEntity.getNotice())

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

        // 카테고리 정보 가져오기
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .cgNum(categoryEntity.getCgNum())
                .cgName(categoryEntity.getCgName())
                .build();
        boardDTO.setCgNum(categoryDTO.getCgNum());
        boardDTO.setCgName(categoryDTO.getCgName());

        boardDTO.setAvg(avg);
        boardDTO.setReviewCnt(reivewCnt.intValue());

        return boardDTO;
    }

    /* 220705 추가 카테고리 ******************************************************/
    PageResultDTO<BoardDTO, Object[]> getCategoryList(PageRequestDTO pageRequestDTO, Long cgNum);
    /* 220705 추가 카테고리 ******************************************************/










}
