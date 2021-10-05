package com.springBootApi.api.domain.model;

import com.springBootApi.api.global.data.Regexp;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Phone {

    @Column(name = "phone", length = 20)
    @NotEmpty
    private String value;

    public static Phone of(final String value) {
        return Phone.builder().value(value).build();
    }

    public String getLocaleNum() {
        if (this.value.startsWith(Regexp.COUNTRY_PHONE_CODE)) {
            return this.value.split(Regexp.COUNTRY_PHONE_CODE)[0];
        }
        return this.value.startsWith(Regexp.DOMESTIC_PHONE_PREFIX) ? Regexp.DOMESTIC_PHONE_CODE : "";
    }
}
