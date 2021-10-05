package com.springBootApi.api.global.util.message;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Message {

    private static MessageSource messageSource;

    public Message(MessageSource messageSource) {
        Message.messageSource = messageSource;
    }

    /**
     * @param properties message_ko.properties 에 정의된 내용
     */
    public static String getMessage(final String properties) {
        return messageSource.getMessage(properties, null, LocaleContextHolder.getLocale());
    }

    /**
     * @param properties message_ko.properties 에 정의된 내용
     * @param arr        properties {0}, {1} 에 들어갈 내용 순차적으로 null 가능하도록 설계
     */
    public static String getMessage(final String properties, final String[] arr) {
        return messageSource.getMessage(properties, arr, LocaleContextHolder.getLocale());
    }
}