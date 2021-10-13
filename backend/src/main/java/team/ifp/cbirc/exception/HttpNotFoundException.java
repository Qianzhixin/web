package team.ifp.cbirc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author GuoXinyuan
 * @date 2021/10/12
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HttpNotFoundException extends RuntimeException {

    public HttpNotFoundException(String message){
        super(message);
    }

}
