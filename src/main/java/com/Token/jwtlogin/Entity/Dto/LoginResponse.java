package com.Token.jwtlogin.Entity.Dto;


import com.Token.jwtlogin.Entity.Authority;
import com.Token.jwtlogin.Entity.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long id;

    private String login_id;

    private String nickname;

    private String name;

    private String email;

    private List<Authority> roles =new ArrayList<>();

    private TokenDto token;

    public LoginResponse(Member member) {
        this.id= member.getId();
        this.login_id= member.getLogin_id();
        this.nickname= member.getNickname();
        this.name= member.getName();
        this.email= member.getEmail();
        this.roles= member.getRoles();
    }
}
