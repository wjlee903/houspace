package com.houspace.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // 테이블
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"category", "member"})
@Table(name = "board")
public class BoardEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bNum;

    private String title;

    @Column(length = 5000)
    private String content;


    //    0704 '주의사항' 칼럼 추가 (오채영)
    @Column(length = 5000)
    private String notice;

    @Column(name = "available_date")
    private String availableDate;

    @Column(name = "available_time")
    private String availableTime;

    private Long price;

    private Long people;

    private String address;

    @Column(length = 50, name = "phonenumber")
    private String phoneNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

}
