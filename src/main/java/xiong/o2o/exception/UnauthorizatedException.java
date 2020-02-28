package xiong.o2o.exception;

import org.apache.shiro.authz.UnauthorizedException;

public class UnauthorizatedException extends RuntimeException {
    public UnauthorizatedException(String msg) {
        super(msg);
    }

    public UnauthorizatedException() {
        super();
    }
}
