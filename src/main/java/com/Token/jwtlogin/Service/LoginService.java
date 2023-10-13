package com.Token.jwtlogin.Service;

import com.Token.jwtlogin.Entity.Authority;
import com.Token.jwtlogin.Entity.Dto.LoginRequest;
import com.Token.jwtlogin.Entity.Dto.LoginResponse;
import com.Token.jwtlogin.Entity.Dto.TokenDto;
import com.Token.jwtlogin.Entity.Member;
import com.Token.jwtlogin.Repository.MemberRepository;
import com.Token.jwtlogin.Security.JwTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwTokenProvider jwTokenProvider;

    public LoginResponse login(LoginRequest req) throws Exception {
        Member member = memberRepository.findByLogin_id(req.getLogin_id()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보 입니다"));

        if (!passwordEncoder.matches(req.getPw(), member.getPw())) {
            throw new BadCredentialsException("잘못된 계정정보 입니다");
        }
  //  member.setRoles(createRefreshToken(member));

        return LoginResponse.builder()
                .id(member.getId())
                .login_id(member.getLogin_id())
                .name(member.getName())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .roles(member.getRoles())
                .token(TokenDto.builder()
                        .access_token(jwTokenProvider.createToken(member.getLogin_id(), member.getRoles()))
                        .refresh_token(member.getRefreshToken())
                        .build())
                .build();
    }
    public boolean register(LoginRequest req) throws Exception {
        try {
            Member member = Member.builder()
                    .login_id(req.getLogin_id())
                    .pw(passwordEncoder.encode(req.getPw()))
                    .name(req.getName())
                    .nickname(req.getNickname())
                    .email(req.getEmail())
                    .build();
            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_MEMBER").build()));
        }catch (Exception e){
            System.out.print(e.getMessage());
            throw new Exception("잘못된 요청입니다");
        }
        return true;
    }

    public LoginResponse getMember(String login_id) throws Exception {
        Member member = memberRepository.findByLogin_id(login_id)
                .orElseThrow(()->new Exception("계정을 찾을 수 없습니다"));
        return new LoginResponse(member);
    }

}
