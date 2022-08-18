package com.houspace.config;

import com.houspace.security.handler.MemberLoginSuccessHandler;
import com.houspace.security.service.CustomUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import static com.houspace.entity.MemberRole.ADMIN;
import static com.houspace.entity.MemberRole.MANAGER;

/*
	프로그램명 : 구글 로그인
	작성자 : 오채영
	최초작성일 : 2022-6-24
	마지막수정일 : 2022-6-28

	사용법 : 메인에 있는 '마이페이지' 버튼 클릭
	        -> 로그인 페이지 (/houspace/login)
	        -> '구글로 로그인' 버튼 클릭
	        -> 구글로그인하면 자동 DB 저장(회원가입)
	        -> 마이페이지에서 닉네임, 이메일 출력
*/
/*
수정 : 2022.06.29.
내용 : 회원 가입및 로그인 기능을 위한 변경
        확인 후 변경가능
 */


@Configuration
@Log4j2
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 자동로그인할 때 사용
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* 추가 - 구글 로그인 페이지 변경 (오채영) *********************************/
        /*
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/houspace/main").permitAll()
                .antMatchers("/houspace/mypage").hasRole("USER")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login();
        */
        /* 추가 - 구글 로그인 페이지 변경 (오채영) */
        /* 이원종 - 추가 로그인폼 변경시 일반 로그인 ***************/
        http.authorizeRequests()
                .antMatchers("/houspace/all").permitAll()
                .antMatchers("/houspace/mypage").hasRole("USER")
                .antMatchers("/houspace/write").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/houspace/loginForm")
                .loginProcessingUrl("/login_proc")
                .failureUrl("/houspace/loginForm/error");

        http.httpBasic();
        http.formLogin();
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/houspace/main");
        http.oauth2Login();
        /* 이원종 - 추가 로그인폼 변경시 일반 로그인 ***************/


//                .userInfoEndpoint()
//                .userService(customOAuth2UserService);


//                .successHandler(successHandler());
//        http.rememberMe().tokenValiditySeconds(60*60*7).userDetailsService(customUserDetailsService);  //7days
    }

//    @Bean
//    public MemberLoginSuccessHandler successHandler() {
//
//        return new MemberLoginSuccessHandler();
//    }


}
