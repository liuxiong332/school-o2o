package xiong.o2o.exception;

public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String msg) {
        super(msg);
    }

    public InvalidParamException() {
        super();
    }
}
