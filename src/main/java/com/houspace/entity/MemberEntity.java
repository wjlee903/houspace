package com.houspace.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // 테이블
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(name = "nick_name_unique", columnNames = "nickName")})
public class MemberEntity {

    @Id
    private String email;

    @Column
    private String pw;

    @Column
    private String nickName;

    @Column
    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();  // roleSet => table 명

    public void addMemberRole(MemberRole memberRole) {

        roleSet.add(memberRole);    // 권한 추가 메서드
    }

}
