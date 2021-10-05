package com.springBootApi.api.domain.user.common.dto;

import com.springBootApi.api.global.util.message.Message;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class TokenDto {
    @ApiModel(value = "토큰 정보")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class Res {

        @ApiModelProperty(value = "엑세스토큰")
        private String accessToken;

        @ApiModelProperty(value = "리프레시 토큰")
        private String refreshToken;

        @ApiModelProperty(value = "메세지")
        private String message;

        /**
         * 토큰 생성 빌더
         * @param accessToken 엑세스 토큰
         * @param refreshToken 리프레시 토큰
         * */
        public static Res of(final String accessToken, final String refreshToken) {
            return Res.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .message(Message.getMessage("token_response.message"))
                    .build();
        }

    }
}