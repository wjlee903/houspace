package com.houspace.entity;

import lombok.*;

import javax.persistence.*;

/*
작성자 : 이용훈
내용 : Entity 작성
작성일 : 22-07-01
 */

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "faq")
public class FaqEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;
}
