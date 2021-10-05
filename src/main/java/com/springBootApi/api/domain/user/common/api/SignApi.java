package com.springBootApi.api.domain.user.common.api;


import com.springBootApi.api.domain.user.common.dto.SignInDto;
import com.springBootApi.api.domain.user.common.dto.SignUpDto;
import com.springBootApi.api.domain.user.common.dto.TokenDto;
import com.springBootApi.api.domain.user.common.factory.SignFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
@Validated
public class SignApi {

    private final SignFactory userSignFactory;

    /**
     * 로그인 처리
     * 유저 타입을 통해서 서비스 객체를 다르게 생성하여 처리한다
     *
     * @param req 아이디, 패스워드, 유저 타입
     * @return 토큰 응답값
     */
    @ApiOperation(value = "로그인", notes = "아이디 이용한 회원 로그인")
    @PostMapping(value = "/sign-in")
    public ResponseEntity<TokenDto.Res> signIn(
            @Valid @RequestBody final SignInDto.Req req
    ) throws IllegalAccessException {
        return ResponseEntity.ok(userSignFactory.getType(req.getUserType()).signIn(req));
    }


    /**
     * 회원 가입 처리
     * 유저 타입을 통해서 서비스 객체를 다르게 생성하여 처리
     *
     * @param req 아이디, 패스워드, 유저 타입
     * @return 토큰 응답값
     */
    @ApiOperation(value = "가입", notes = "회원가입 요청")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<SignUpDto.Res> signUp(
            @Valid @RequestBody final SignUpDto.Req req
    ) throws IllegalAccessException {
        return ResponseEntity.ok(userSignFactory.getType(req.getUserType()).signUp(req));
    }

}