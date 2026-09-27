package emlyn.ma.wallet.common.contract;

import emlyn.ma.wallet.common.error.ErrorCode;
import emlyn.ma.wallet.common.error.SysErrorCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final String code;
    private final String desc;
    private final T      data;

    public ApiResponse(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.desc = errorCode.getDesc();
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SysErrorCode.SUCCESS, data);
    }

    public static <T> ApiResponse<T> failure(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode, null);
    }

    public boolean successful() {
        return SysErrorCode.SUCCESS.getCode().equals(this.code);
    }

}
