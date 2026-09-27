package emlyn.ma.wallet.common.exception;

import emlyn.ma.wallet.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class SysException extends RuntimeException {

    private final ErrorCode errorCode;

    public SysException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

}
