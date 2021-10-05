package com.springBootApi.api.global.config.security;

import com.springBootApi.api.domain.user.common.model.UserType;
import com.springBootApi.api.global.config.security.exception.CustomAccessDeniedHandler;
import com.springBootApi.api.global.config.security.exception.CustomAuthenticationEntryPoint;
import com.springBootApi.api.global.config.security.token.filter.JwtAuthenticationFilter;
import com.springBootApi.api.global.config.security.token.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }

    /**
     * rest api 이기에 httpBasic disable
     * csrf 보안 또한 rest api 이기에 불필요
     * jwt token 사용하여 인증하기 때문에 세션 사용X
     * request 권한 체크의 경우 해당 url 모두 허용
     * 이외의 경우 USER 권한 있는 경우에만 허용
     * jwt token 필터를 id/password 인증 필터 전에 삽입
     *
     * @param http security 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                // 가입 및 인증 주소는 누구나 접근가능
                .antMatchers("/swagger-ui/**", "/*/sign/**", "/*/sign-in", "/*/sign-up",
                        "/v1/employer/company/**", "/v1/export/file/**").permitAll()
                .antMatchers("/*/member/**").hasAnyAuthority(UserType.M.getValue())
                .antMatchers("/*/employer/**").hasAnyAuthority(UserType.A.getValue())
                .anyRequest().hasAnyAuthority(UserType.M.getValue(), UserType.A.getValue())
                .and()
                // 토큰 예외 발생시
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                // 권한 예외 발생시
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * swagger 리소스 체크 무시
     *
     * @param web 웹시큐리티
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui/index.html", "/webjars/**", "/swagger/**");
    }
}