package com.springBootApi.api.global.config.security.token.repository;

import com.springBootApi.api.global.config.security.token.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    RefreshToken findByEmailAndValue(String email, String value);

    RefreshToken findByEmail(String email);

    boolean existsByEmail(String email);
}
