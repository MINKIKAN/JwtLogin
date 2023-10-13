package com.Token.jwtlogin.Entity.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private Long id;

    private String login_id;

    private String pw;

    private String nickname;

    private String name;

    private String email;

}
