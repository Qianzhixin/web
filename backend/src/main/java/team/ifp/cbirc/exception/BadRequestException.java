package team.ifp.cbirc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 * Bad Request 400
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }

}
