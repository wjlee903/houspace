package com.houspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String email;
    private String pw;
    private String nickName;
    private boolean fromSocial;
    private int roleSet;


}
