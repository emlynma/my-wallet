package emlyn.ma.wallet.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysErrorCode implements ErrorCode {

    SUCCESS("200", "success"),
    UNKNOWN("500", "system error"),
    ;

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return print();
    }

}
