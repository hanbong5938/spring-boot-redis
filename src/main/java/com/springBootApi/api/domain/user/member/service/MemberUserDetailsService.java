package com.springBootApi.api.domain.user.member.service;


import com.springBootApi.api.domain.user.common.exception.SignInFailException;
import com.springBootApi.api.domain.user.member.model.Member;
import com.springBootApi.api.domain.user.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 정보 불러오는 로직
     * @param userPk pk 값
     * */
    @Override
    public UserDetails loadUserByUsername(final String userPk) throws UsernameNotFoundException {
        return memberRepository.findById(Long.valueOf(userPk))
                .orElseThrow(SignInFailException::new);
    }
}