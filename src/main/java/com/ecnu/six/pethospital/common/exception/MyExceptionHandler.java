package com.ecnu.six.pethospital.common.exception;

import com.ecnu.six.pethospital.common.ResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author onion
 * @date 2021/3/22 -9:42 上午
 */
@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler
    public ResponseData runtimeExceptionHandler(RuntimeException e) {
        return new ResponseData(-1, e.getLocalizedMessage(), null);
    }
}
