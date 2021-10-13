package team.ifp.cbirc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 * Bad Request 400
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HttpBadRequestException extends RuntimeException{

    public HttpBadRequestException(String message) {
        super(message);
    }

}
