package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
    private String code;
    private String message;
    private Object data;

    public static CommonResponse of(String code, String message) {
        return CommonResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

    public static CommonResponse of(String code, String message, Object data) {
        return CommonResponse.builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static CommonResponse success() {
        return of("0000", "success");
    }

    public static CommonResponse success(Object data) {
        return of("0000", "success", data);
    }

    public static CommonResponse failed() {
        return of("9999", "failed");
    }

    public static CommonResponse failed(String message) {
        return of("9999", message);
    }
}
