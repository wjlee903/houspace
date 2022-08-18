package com.houspace.security.service;

import com.houspace.entity.MemberEntity;
import com.houspace.entity.MemberRole;
import com.houspace.repository.MemberRepository;
import com.houspace.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
public class OAuth2UserDetailsService  extends DefaultOAuth2UserService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2User oAuth2User =  super.loadUser(userRequest);

        log.info("==============================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k +":" + v);
        });


        String email = null;
        String nickName = null;

        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
            nickName = oAuth2User.getAttribute("name");
        }


        MemberEntity member = saveSocialMember(email, nickName);

        AuthMemberDTO clubAuthMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPw(),
                true,   //fromSocial
                member.getRoleSet().stream().map(
                                role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        clubAuthMember.setName(member.getNickName());


        return clubAuthMember;

    }


    private MemberEntity saveSocialMember(String email, String nickName){

        //기존에 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회만
        Optional<MemberEntity> result = repository.findByEmail(email, true);

        if(result.isPresent()){
            return result.get();
        }

        //없다면 회원 추가 패스워드는 1111 이름은 그냥 이메일 주소로
       MemberEntity member = MemberEntity.builder().email(email)
                .nickName(nickName)
                .pw( passwordEncoder.encode("1111") )
                .fromSocial(true)
                .build();

       member.addMemberRole(MemberRole.USER);


        repository.save(member);

        return member;
    }
}
