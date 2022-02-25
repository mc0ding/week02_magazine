package com.spring.week02magazine.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
    private String result;
    private String msg;
}