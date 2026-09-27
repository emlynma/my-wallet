package emlyn.ma.wallet.common.error;

public interface ErrorCode {

    String getCode();

    String getDesc();

    default String print() {
        return getDesc() + "(" + getCode() + ")";
    }

}
