package com.houspace.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
	프로그램명 : 구글 로그인
	작성자 : 오채영
	최초작성일 : 2022-6-24
	마지막수정일 : 2022-6-27

	사용법 : 구글 로그인 성공 후 쓰는 코드. 사용여부 미정.
*/



@Log4j2
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {


        log.info("구글 로그인 성공!");
    }
}

