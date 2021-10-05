package com.springBootApi.api.domain.user.admin.repository;

import com.springBootApi.api.domain.model.Phone;
import com.springBootApi.api.domain.user.admin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByPhone(Phone of);
}
