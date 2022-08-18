package com.houspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long bNum;
    private String title;
    private String content;


    //    0704 '주의사항' 칼럼 추가 (오채영)
    private String notice;

    private String availableDate;
    private String availableTime;
    private Long price;
    private Long people;
    private String address;
    private String phoneNumber;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    // 리뷰 수 jpa이 count
    private int reviewCnt;

    // 평균 평점
    private double avg;

    // category name
    private Long cgNum;
    private String cgName;

    // Member
    private String email;
    //    private String pw;
    private String nickName;
    private boolean fromSocial;
    private int roleSet;

    /* 수정 해보는 중 book */
    // BookInfo
    private Long bookNum;
    private LocalDateTime bookClickDate;


    @Builder.Default
    private List<ImageDTO> imageDTOList = new ArrayList<>();

}
