package com.houspace.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // 테이블
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"board", "member"})
@Table(name = "book_info")
@EntityListeners(value = {AuditingEntityListener.class})
public class BookInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookNum;

    @LastModifiedDate
    @Column(name = "book_click_date")
    private LocalDateTime bookClickDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;



}
