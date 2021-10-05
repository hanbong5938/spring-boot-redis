package com.springBootApi.api.infra.sms.factory;

import com.springBootApi.api.domain.model.Phone;
import com.springBootApi.api.global.data.Regexp;
import com.springBootApi.api.infra.sms.DomesticSms;
import com.springBootApi.api.infra.sms.GlobalSms;
import com.springBootApi.api.infra.sms.SmsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsClientFactory {
    private final GlobalSms globalSms;
    private final DomesticSms domesticSms;

    public SmsClient getType(final Phone phone) {

        final SmsClient SmsClient;
        if (Regexp.DOMESTIC_PHONE_CODE.equals(phone.getLocaleNum())) {
            SmsClient = domesticSms;
        } else {
            SmsClient = globalSms;
        }
        return SmsClient;
    }
}