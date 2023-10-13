package com.Token.jwtlogin.Service;

import com.Token.jwtlogin.Entity.Member;
import com.Token.jwtlogin.Repository.MemberRepository;
import com.Token.jwtlogin.Security.CustomMemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member =memberRepository.findByLogin_id(username)
                .orElseThrow(() -> new UsernameNotFoundException("인증오류"));

        return new CustomMemberDetails(member);
    }
}
