package com.Token.jwtlogin.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.token.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
