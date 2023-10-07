package com.student.zhaokangwei.handler;

import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.utils.ViewUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ServiceValidationException.class})
    public Object catchServiceValidationException(ServiceValidationException exception, HttpServletResponse response) {
        log.error("catchServiceValidationException捕获到异常了=====>" + exception);
        return getResult(response, exception.getMessage(), exception.getState());
    }

    @ExceptionHandler({ValidationException.class})
    public Object catchValidationException(ValidationException exception, HttpServletResponse response) {
        log.error("catchValidationException捕获到异常了=====>" + exception);
        return getResult(response, exception.getMessage(), 400);
    }


//    @ExceptionHandler({Exception.class})
//    public Object catchOtherException(Exception exception, HttpServletResponse response) {
//        log.error("catchOtherException捕获到其它异常了=====>" + exception);
//        exception.printStackTrace();
//        return getResult(response, "服务器端发生了异常", 500);
//    }

    /**
     * 获取结果
     *
     * @param message
     * @param state
     * @return
     */
    private String getResult(HttpServletResponse response, String message, int state) {
        response.setStatus(state);
        return ViewUtils.view(message, null, state);
    }

}

