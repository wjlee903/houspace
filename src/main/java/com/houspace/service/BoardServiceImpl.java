package com.houspace.service;

import com.houspace.dto.BoardDTO;
import com.houspace.dto.PageRequestDTO;
import com.houspace.dto.PageResultDTO;
import com.houspace.entity.BoardEntity;
import com.houspace.entity.CategoryEntity;
import com.houspace.entity.ImageEntity;
import com.houspace.entity.MemberEntity;
import com.houspace.repository.BoardRepository;
import com.houspace.repository.ImageRepository;
import com.houspace.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/*
작성자 : 이원종
내용 : 게시글 작성
        메인 페이지 게시글 페이징 가져오기
        상세보기 페이지 게시글 번호로 가져오기
 */

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("bNum").descending());

        Page<Object[]> result = boardRepository.getListPage(pageable);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ getListPage : " + result);
        result.getContent().forEach(arr -> {
            log.info("@@@@@@@@@@@@@@@@@ arr : " + Arrays.toString(arr));
        });

        Function<Object[], BoardDTO> fn = (arr -> entitiesToDTO(
                (BoardEntity) arr[0],
                (List<ImageEntity>) (Arrays.asList((ImageEntity) arr[1])),
                (MemberEntity) arr[2],
                (CategoryEntity) arr[3],
                (Double) arr[4],
                (Long) arr[5]
        ));

        return new PageResultDTO<>(result, fn);

    }

    @Transactional
    @Override
    public Long write(BoardDTO boardDTO) {
        Map<String, Object> entityMap = dtoToEntity(boardDTO);
        BoardEntity boardEntity = (BoardEntity) entityMap.get("board");    // get 메서드 리턴타입은 Object
        List<ImageEntity> imageEntityList = (List<ImageEntity>) entityMap.get("imgList");

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ service boardentity : " + boardEntity);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ service imageEntityList : " + imageEntityList);

        boardRepository.save(boardEntity);

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ service imageEntityList : " + imageEntityList);
        imageEntityList.forEach(image -> {
            imageRepository.save(image);
        });
        return boardEntity.getBNum();
    }

    @Override
    public BoardDTO getBoard(Long bNum) {

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ getBoard : " + (boardRepository.getBoardWithAll(bNum)).get(0)[0]);
        // board 글번호로 게시물 읽어옴
        List<Object[]> result = boardRepository.getBoardWithAll(bNum);
        // board 객체 꺼내기
        BoardEntity boardEntity = (BoardEntity) result.get(0)[0];
        // 이미지를 담을 List 셍성
        List<ImageEntity> imageEntityList = new ArrayList<>();
        // 반복문을 이용하여 이미지 List에 저장
        result.forEach(arr -> {
            ImageEntity imageEntity = (ImageEntity) arr[1];
            imageEntityList.add(imageEntity);
        });
        // 멤버
        MemberEntity memberEntity = (MemberEntity) result.get(0)[2];
        // 카테고리
        CategoryEntity categoryEntity = (CategoryEntity) result.get(0)[3];

        // 평균
        Double avg = (Double) result.get(0)[4];
        // 리뷰 수
        Long reviewCnt = (Long) result.get(0)[5];

        return entitiesToDTO(boardEntity, imageEntityList, memberEntity, categoryEntity, avg, reviewCnt);

    }

    /* 220705 추가 카테고리 ******************************************************/
    @Override
    public PageResultDTO<BoardDTO, Object[]> getCategoryList(PageRequestDTO pageRequestDTO, Long cgNum) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("bNum").descending());

        Page<Object[]> result = boardRepository.getCategoryListPage(pageable, cgNum);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ getListPage : " + result);
        result.getContent().forEach(arr -> {
            log.info("@@@@@@@@@@@@@@@@@ arr : " + Arrays.toString(arr));
        });

        Function<Object[], BoardDTO> fn = (arr -> entitiesToDTO(
                (BoardEntity) arr[0],
                (List<ImageEntity>) (Arrays.asList((ImageEntity) arr[1])),
                (MemberEntity) arr[2],
                (CategoryEntity) arr[3],
                (Double) arr[4],
                (Long) arr[5]
        ));

        return new PageResultDTO<>(result, fn);
    }
    /* 220705 추가 카테고리 ******************************************************/








}
