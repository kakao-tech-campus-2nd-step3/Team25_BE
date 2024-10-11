package com.team25.backend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team25.backend.dto.response.KakaoErrorResponse;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().is5xxServerError() ||
                httpResponse.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoErrorResponse kakaoErrorResponse = objectMapper.readValue(httpResponse.getBody(), KakaoErrorResponse.class);

        int code = kakaoErrorResponse.code();

        switch (code) {
            case -1:
                throw new CustomException(ErrorCode.KAKAO_PLATFORM_ERROR);
            case -2:
                throw new CustomException(ErrorCode.KAKAO_TOKEN_FORMAT_ERROR);
            case -401:
                throw new CustomException(ErrorCode.KAKAO_EXPIRED_TOKEN);
            default:
        }
    }
}
