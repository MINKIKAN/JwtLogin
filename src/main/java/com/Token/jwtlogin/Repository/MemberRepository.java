package com.Token.jwtlogin.Repository;

import com.Token.jwtlogin.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
public interface MemberRepository extends JpaRepository<Member, Long > {
    Optional<Member> findByLogin_id(String login_id);
}
