package com.houspace.dto;

import com.houspace.entity.BoardEntity;
import com.houspace.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long reviewNum;
    private String text;
    private Long grade;

    // Board
    private Long bnum;

    // Member
    private String email;
    private String nickName;

    private LocalDateTime regDate, modDate;

}
