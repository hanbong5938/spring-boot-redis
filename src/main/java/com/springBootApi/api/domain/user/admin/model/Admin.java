package com.springBootApi.api.domain.user.admin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springBootApi.api.domain.model.DateTime;
import com.springBootApi.api.domain.model.Email;
import com.springBootApi.api.domain.model.Phone;
import com.springBootApi.api.domain.user.common.model.UserType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Admin extends DateTime implements UserDetails {

    /**
     * pk 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    Long id;

    /**
     * 전화번호
     */
    @Embedded
    private Phone phone;

    /**
     * 비밀번호
     */
    @Column
    private String password;

    /**
     * 이름
     */
    @Column(length = 40)
    private String name;

    /**
     * 이메일
     */
    @Embedded
    private Email email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(UserType.A.getValue()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
