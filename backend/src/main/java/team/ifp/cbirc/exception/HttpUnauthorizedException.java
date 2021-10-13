package team.ifp.cbirc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 * Unauthorized 401
 */

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class HttpUnauthorizedException extends RuntimeException {

    public HttpUnauthorizedException(String message) {
        super(message);
    }

}
