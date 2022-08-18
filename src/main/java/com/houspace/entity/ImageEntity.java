package com.houspace.entity;

import lombok.*;

import javax.persistence.*;

@Entity // 테이블
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgNum;

    @Column(name = "img_name")
    private String imgName;

    @Column
    private String path;

    @Column
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;






}
