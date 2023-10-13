package com.Token.jwtlogin.Controller;

import com.Token.jwtlogin.Entity.Dto.LoginRequest;
import com.Token.jwtlogin.Entity.Dto.LoginResponse;
import com.Token.jwtlogin.Entity.Dto.TokenDto;
import com.Token.jwtlogin.Repository.MemberRepository;
import com.Token.jwtlogin.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SignUpAndLoginController {
    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        return new ResponseEntity<>(loginService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody LoginRequest request) throws Exception {
        return new ResponseEntity<>(loginService.register(request), HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public ResponseEntity<LoginResponse> getUser(@RequestParam String account) throws Exception {
        return new ResponseEntity<>(loginService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/admin/get")
    public ResponseEntity<LoginResponse> getUserForAdmin(@RequestParam String account) throws Exception {
        return new ResponseEntity<>(loginService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto token) throws Exception {
        return new ResponseEntity<>(loginService.refreshAccessToken(token), HttpStatus.OK);
    }
}
