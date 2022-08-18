package com.houspace.service;

import com.houspace.dto.BoardDTO;
import com.houspace.dto.MemberDTO;
import com.houspace.dto.PageRequestDTO;
import com.houspace.dto.PageResultDTO;
import com.houspace.entity.BoardEntity;
import com.houspace.entity.BookInfoEntity;
import com.houspace.entity.ImageEntity;
import com.houspace.entity.MemberEntity;
import com.houspace.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/*
작성자 : 이원종
내용 : 회원가입을 위한 메소드 작성
 */
@Service
@Transactional  // 2022.07.02. 추가 //////////////////////////////
@Log4j2
@RequiredArgsConstructor
public class MemberServieImpl implements MemberService {

    private final MemberRepository memberRepository;

    /* 회원가입 ******************************************************************/
    @Override
    public String joinMember(MemberDTO memberDTO) {
        // 회원가입 메소드
        MemberEntity memberEntity = dtoToEntity(memberDTO);
        memberRepository.save(memberEntity);
        return "가입을 축하합니다.";
    }
    /*
    @Override
    public String joinMember(MemberDTO memberDTO) {
        Optional<MemberEntity> member = memberRepository.findByEmail(memberDTO.getEmail(), false);
        if (member == null) {
            // 회원가입 메소드
            MemberEntity memberEntity = dtoToEntity(memberDTO);
            memberRepository.save(memberEntity);
            return "회원가입에 성공하셨습니다.";
        } else {
            return "회원";
        }
    }
     */
    /* 회원가입 ******************************************************************/

    /* 추가 - 마이페이지 내가 예약한 글 가져오기 ********************************************/
    @Override
    public PageResultDTO<BoardDTO, Object[]> getMyBookList(String email, PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("bNum").descending());
        Page<Object[]> result = memberRepository.getMyBookList(email, pageable);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ getListPage : " + result);
        result.getContent().forEach(arr -> {
            log.info("@@@@@@@@@@@@@@@@@ arr : " + Arrays.toString(arr));
        });

        Function<Object[], BoardDTO> fn = (arr -> entitiesToDTO(
                (BoardEntity) arr[0],
                (List<ImageEntity>) (Arrays.asList((ImageEntity) arr[1])),
                (MemberEntity) arr[2],
                (BookInfoEntity) arr[3],
                (Double) arr[4],
                (Long) arr[5]
        ));
        return new PageResultDTO<>(result, fn);
    }
    /* 추가 - 마이페이지 내가 예약한 글 가져오기 ********************************************/

    /* 마이페이지 > 내 게시글 목록 (오채영 0701) ********************************************/
    @Override
    public PageResultDTO<BoardDTO, Object[]> getMyBoardList(String email, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("bNum").descending());
        Page<Object[]> myResult = memberRepository.getMyBoardList(email, pageable);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ getListPage : " + myResult);
        myResult.getContent().forEach(arr -> {
            log.info("@@@@@@@@@@@@@@@@@ myArr : " + Arrays.toString(arr));
        });

        Function<Object[], BoardDTO> fn = (arr -> entitiesToDTO(
                (BoardEntity) arr[0],
                (List<ImageEntity>) (Arrays.asList((ImageEntity) arr[1])),
                (MemberEntity) arr[2],
                (BookInfoEntity) arr[3],
                (Double) arr[4],
                (Long) arr[5]
        ));
        return new PageResultDTO<>(myResult, fn);
    }
    /* 마이페이지 > 내 게시글 목록 (오채영 0701) */


    /* 2022.07.03. 추가 - 회원가입시 이메일 확인 ************************/
    @Override
    public String serachEmail(String email) {
        Optional<Object> result = memberRepository.searchByEmail(email);
        if (result.isEmpty()) {
            return "사용 가능한 이메일입니다.";
        } else
            return "사용할 수 없는 이메일입니다.";
    }
    /* 2022.07.03. 추가 - 회원가입시 이메일 확인 ************************/

    /* 2022.07.03. 추가 - 회원가입시 닉네임 확인 ************************/
    @Override
    public String serachNickName(String nickName) {
        Optional<Object> result = memberRepository.findByNickName(nickName);
        if (result.isEmpty()) {
            return "사용 가능한 닉네임입니다.";
        } else
            return "사용할 수 없는 닉네임입니다.";
    }
    /* 2022.07.03. 추가 - 회원가입시 닉네임 확인 ************************/

}
