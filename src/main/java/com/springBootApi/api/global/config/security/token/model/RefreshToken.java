package com.springBootApi.api.global.config.security.token.model;

import com.springBootApi.api.domain.model.Email;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;

import static com.springBootApi.api.global.data.Token.REFRESH_TOKEN;
import static com.springBootApi.api.global.data.Token.REFRESH_TOKEN_TIME;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@RedisHash(value = REFRESH_TOKEN, timeToLive = REFRESH_TOKEN_TIME)
@TypeAlias(REFRESH_TOKEN)
public class RefreshToken {

    @Id
    @Indexed
    private Long id;

    @Indexed
    private String email;

    @Indexed
    private String value;

    public static RefreshToken of(final Email email, final String token) {
        return RefreshToken.builder()
                .email(email.getValue())
                .value(token)
                .build();
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

}
