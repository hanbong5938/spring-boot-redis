package com.springBootApi.api.global.config.security.token.provider;

import com.springBootApi.api.domain.user.common.factory.UserDetailsFactory;
import com.springBootApi.api.domain.user.common.model.UserType;
import com.springBootApi.api.global.config.security.token.exception.ExpiredTokenException;
import com.springBootApi.api.global.data.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final UserDetailsFactory userDetailsFactory;

    /**
     * 밀리 초 단위
     * 1000 * 60 * 60 = 1시간
     */
    private final long ACCESS_TOKEN_VALIDATION_SECOND = 1000L * 60 * 1;
    private final long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 7 * 2;

    /**
     * 토큰 생성용 secretKey
     */
    @Value("spring.jwt.secret")
    private String secretKey;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * jwt 토큰 생성
     *
     * @param userPk   user 테이블 pk 값
     * @param userType 유저 권한
     *                 claims -> 데이터
     *                 issuedAt -> 토큰 발행일자
     *                 expiration -> 토큰 만료일 (생성일 + 만료 시간)
     *                 signWith -> 암호화 알고리즘, 시크릿키
     */
    public String generateToken(final String userPk, final UserType userType) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("userType", userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALIDATION_SECOND))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(final String userPk, final UserType userType) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("userType", userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALIDATION_SECOND))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * jwt 토큰 사용하여 인증 정보 조회
     *
     * @param token 회원정보 추출하기 위한 토큰
     */
    public Authentication getAuthentication(final String token) {
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsFactory.getType(this.getRoles(token))
                    .loadUserByUsername(this.getUserPk(token));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assert userDetails != null;
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * jwt 토큰에서 pk 값 추출
     *
     * @param token pk 추출 위한 토큰
     * @return pk 값
     */
    public String getUserPk(final String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * jwt 토큰에서 롤 추출
     *
     * @param token 토큰 값
     * @return userType enum
     */
    public UserType getRoles(final String token) {
        return UserType.valueOf(Jwts.parser()
                .setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userType").toString());
    }


    /**
     * Request Header 에서 token 파싱
     *
     * @param request http 헤더 X-AUTH-TOKEN : jwt 토큰
     */
    public String resolveToken(final HttpServletRequest request) {
        return request.getHeader(Token.AUTH);
    }

    /**
     * jwt 토큰의 유효성 + 만료 일자 확인
     *
     * @param jwtToken 만료일자 확인 위한 토큰 값
     */
    public boolean isValidateToken(final String jwtToken) {
        try {
            final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExpiredTokenException();
        }
    }
}