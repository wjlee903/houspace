package com.houspace.dto;

import com.houspace.entity.BoardEntity;
import com.houspace.entity.CategoryEntity;
import com.houspace.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoDTO {

    private Long bookNum;
    private LocalDateTime bookClickDate;

    // Member
    private String email;
    private String nickName;
    private int roleSet;

    // Board
    private Long bNum;
    private String title;
    private String content;

    // 0704 '주의사항' 칼럼 추가 (오채영)
    private String notice;

    private LocalDateTime availableDate;
    private String availableTime;
    private Long price;
    private Long people;
    private String address;
    private String phoneNumber;

    // Category
    private String cgName;

}
