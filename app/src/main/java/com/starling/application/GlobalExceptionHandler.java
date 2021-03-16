package com.starling.application;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.FeignClientException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleFeignExceptionForbiddenError(FeignException.FeignClientException.Forbidden e) {
        log.info("Forbidden encountered: {}", e.getMessage());
    }

    @ExceptionHandler(FeignException.FeignClientException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleFeignExceptionInternalServerError(FeignException.FeignClientException.InternalServerError e) {
        log.error("Internal server error encountered: {}", e.getMessage());
    }

}
