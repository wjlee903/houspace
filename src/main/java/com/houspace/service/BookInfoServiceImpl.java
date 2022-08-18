package com.houspace.service;

import com.houspace.dto.BookInfoDTO;
import com.houspace.entity.BookInfoEntity;
import com.houspace.repository.BookInfoReposiory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class BookInfoServiceImpl implements BookInfoService {

    private final BookInfoReposiory bookInfoReposiory;

    /* 예약정보 DB에 저장 ***********************************************************/
    @Override
    public Long bookingBoard(BookInfoDTO bookInfoDTO) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ service bookInfoDTO : " + bookInfoDTO);
        BookInfoEntity bookInfoEntity = dtoToEntity(bookInfoDTO);
        bookInfoReposiory.save(bookInfoEntity);
        return bookInfoEntity.getBookNum();
    }


    /* 예약정보 DB에 저장 ***********************************************************/

    /* 2022.07.01. 예약 취소 추가 ********************************************/
    @Override
    public void bookDelete(Long bookNum) {
        bookInfoReposiory.deleteById(bookNum);
    }
    /* 2022.07.01. 예약 취소 추가 ********************************************/

    /* 추가 0704 예약시 버튼 비활성화 ***************************/
    @Override
    public boolean getBook(Long bNum) {
        Optional<Object> result = bookInfoReposiory.getBoardBook(bNum);
        if (result.isPresent()) {
            return true;
        }
        return false;
    }
    /* 추가 0704 예약시 버튼 비활성화 ***************************/

    /* 추가 0707 내가 예약한 글에서만 리뷰버튼 보이기 *****************/
    @Override
    public String getBookEmail(Long bNum) {
        Optional<Object> result = bookInfoReposiory.getBoardBook(bNum);
        if (result.isPresent()) {
            BookInfoEntity bookInfoEntity = (BookInfoEntity) result.get();
            return bookInfoEntity.getMember().getEmail();
        }
        return "nobooking";
    }
    /* 추가 0707 내가 예약한 글에서만 리뷰버튼 보이기 *****************/



}