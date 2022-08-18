package com.houspace.entity;

import lombok.*;

import javax.persistence.*;

@Entity // 테이블
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"board", "member"})
@Table(name = "review")
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNum;

    @Column(length = 2000)
    private String text;

    @Column
    private Long grade;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    public void changeGrade(Long grade) {

        this.grade = grade;
    }

    public void changeText(String text) {

        this.text = text;
    }


}
