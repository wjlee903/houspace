package com.houspace.entity;

import lombok.*;

import javax.persistence.*;

@Entity // 테이블
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cg_num")
    private Long cgNum;

    @Column(length = 255, nullable = false, name = "cg_name")
    private String cgName;








}
