package com.springBootApi.api.domain.user.admin.service;

import com.springBootApi.api.domain.model.Phone;
import com.springBootApi.api.domain.user.admin.model.Admin;
import com.springBootApi.api.domain.user.admin.repository.AdminRepository;
import com.springBootApi.api.domain.user.common.dto.SignInDto;
import com.springBootApi.api.domain.user.common.dto.SignUpDto;
import com.springBootApi.api.domain.user.common.dto.TokenDto;
import com.springBootApi.api.domain.user.common.exception.SignInFailException;
import com.springBootApi.api.domain.user.common.model.UserType;
import com.springBootApi.api.domain.user.common.service.SignService;
import com.springBootApi.api.global.config.security.token.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSignService implements SignService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public SignUpDto.Res signUp(final SignUpDto.Req req) {
        try {
            adminRepository.save(req.toAdmin());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return SignUpDto.Res.of();
    }

    @Override
    @Transactional
    public TokenDto.Res signIn(SignInDto.Req req) {
        final Admin admin = adminRepository.findByPhone(Phone.of(req.getId())).orElseThrow(
                SignInFailException::new
        );

        if (!passwordEncoder.matches(req.getPassword(), admin.getPassword())) {
            throw new SignInFailException();
        }

        final String id = String.valueOf(admin.getId());
        return TokenDto.Res.of(jwtTokenProvider.generateToken(id, UserType.A)
                , jwtTokenProvider.generateRefreshToken(id, UserType.A));
    }
}
