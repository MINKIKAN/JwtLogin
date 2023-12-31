package com.Token.jwtlogin.Entity.Dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String access_token;
    private String refresh_token;
}