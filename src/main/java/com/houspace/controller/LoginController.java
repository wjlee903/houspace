package com.houspace.controller;

import com.houspace.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
작성자 : 이원종
일자 : 2022.07.03.
내용 : 회원가입시 이메일 및 닉네임 중복 확인
 */
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;

    /* 2022.07.03. 추가 - 회원가입 이메일 중복 확인 ***********************************/
    @PostMapping("/email/{email}")
    public ResponseEntity<String> searchEmail(@PathVariable("email") String email) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ searchEmail : " + email);
        String logEmailResult = memberService.serachEmail(email);
        return new ResponseEntity<>(logEmailResult, HttpStatus.OK);
    }
    /* 2022.07.03. 추가 - 회원가입 이메일 중복 확인 ***********************************/

    /* 2022.07.03. 추가 - 회원가입 이메일 중복 확인 ***********************************/
    @PostMapping("/nickname/{nickName}")
    public ResponseEntity<String> searchNickName(@PathVariable("nickName") String nickName) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ search nickName : " + nickName);
        String logNickNameResult = memberService.serachNickName(nickName);
        return new ResponseEntity<>(logNickNameResult, HttpStatus.OK);
    }
    /* 2022.07.03. 추가 - 회원가입 이메일 중복 확인 ***********************************/


}
