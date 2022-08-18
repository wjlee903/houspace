package com.houspace.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/*
	프로그램명 : 구글 로그인
	작성자 : 오채영
	최초작성일 : 2022-6-24
	마지막수정일 : 2022-6-27

	사용법 : 메인에 있는 '마이페이지' 버튼 클릭
	        -> 로그인 페이지
	        -> 구글로그인하면 자동 DB 저장(회원가입)
	        -> 마이페이지에서 닉네임, 이메일 출력
*/



@Log4j2
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    private Map<String, Object> attr;

    public AuthMemberDTO(String username, String password, boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username,password, fromSocial, authorities);
        this.attr = attr;
    }

    public AuthMemberDTO(String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;

    }

    @Override
    public Map<String, Object> getAttributes() {

        return this.attr;
    }
}
