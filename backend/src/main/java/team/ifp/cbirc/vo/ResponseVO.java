package team.ifp.cbirc.vo;

import lombok.Data;
import team.ifp.cbirc.exception.BadRequestException;
import team.ifp.cbirc.exception.UnauthorizedException;

@Data
public class ResponseVO {

    private ResponseVO(){};

    private Object content;

    /**
     * OK 200
     * @return
     */
    public static ResponseVO buildOK(){
        return new ResponseVO();
    }

    /**
     * OK 200
     * @param content
     * @return
     */
    public static ResponseVO buildOK(Object content){
        ResponseVO response = new ResponseVO();
        response.setContent(content);
        return response;
    }

    /**
     * Internet Server Error 500
     * @param message
     */
    public static void buildInternetServerError(String message){
        throw new RuntimeException(message);
    }

    /**
     * Bad Request 400
     * @param message
     */
    public static void buildBadRequest(String message) {
        throw new BadRequestException(message);
    }

    /**
     * Unauthorized
     * @param message
     */
    public static void buildUnauthorized(String message) {
        throw new UnauthorizedException(message);
    }

}
