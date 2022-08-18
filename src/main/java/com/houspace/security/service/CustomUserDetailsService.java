package com.houspace.security.service;

import com.houspace.entity.MemberEntity;
import com.houspace.repository.MemberRepository;
import com.houspace.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

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
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MemberEntity> result = memberRepository.findByEmail(username, false);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check User Email or from Social ");
        }

        MemberEntity member = result.get();

        log.info("-----------------------------");
        log.info(member);

        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPw(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );

        authMember.setName(member.getNickName());


        return authMember;
    }
}
