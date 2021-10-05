package com.springBootApi.api.domain.user.member.repository;

import com.springBootApi.api.domain.model.Phone;
import com.springBootApi.api.domain.user.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPhone(Phone of);
}
