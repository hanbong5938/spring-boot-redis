package com.springBootApi.api.domain.user.admin.service;

import com.springBootApi.api.domain.user.admin.model.Admin;
import com.springBootApi.api.domain.user.admin.repository.AdminRepository;
import com.springBootApi.api.domain.user.common.exception.SignInFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    /**
     * 정보 불러오는 로직
     * @param userPk pk값
     * */
    @Override
    public UserDetails loadUserByUsername(final String userPk) throws UsernameNotFoundException {
        return adminRepository.findById(Long.valueOf(userPk))
                .orElseThrow(SignInFailException::new);
    }
}