package emlyn.ma.wallet.component.web;

import emlyn.ma.wallet.common.contract.ApiResponse;
import emlyn.ma.wallet.common.error.ErrorCode;
import emlyn.ma.wallet.common.error.SysErrorCode;
import emlyn.ma.wallet.common.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SysException.class)
    public ApiResponse<?> handleException(SysException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.error("{}", errorCode, exception);
        return ApiResponse.failure(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception exception) {
        log.error("{}", SysErrorCode.UNKNOWN, exception);
        return ApiResponse.failure(SysErrorCode.UNKNOWN);
    }

}
