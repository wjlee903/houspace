package com.houspace.repository;

import com.houspace.entity.BookInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/*
작성자 : 이원종
일시 : 2022.06.30.
내용 : 예약 관련 CRUD
 */
public interface BookInfoReposiory extends JpaRepository<BookInfoEntity, Long> {

    /* 추가 0704 예약시 버튼 비활성화 ***************************/
    @Query("select bi from BoardEntity b " +
            "left outer join BookInfoEntity bi on bi.board = b " +
            "where bi.board.bNum =:bNum ")
    Optional<Object> getBoardBook(Long bNum);
    /* 추가 0704 예약시 버튼 비활성화 ***************************/
}
