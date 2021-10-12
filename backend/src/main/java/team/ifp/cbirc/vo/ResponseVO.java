package team.ifp.cbirc.vo;

import lombok.Data;
import team.ifp.cbirc.exception.BadRequestException;
import team.ifp.cbirc.exception.UnauthorizedException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
public class ResponseVO {

    private ResponseVO(){};

    private int code;

    private Object content;

    /**
     * OK 200
     * @param content
     * @return
     */
    public static ResponseVO buildOK(Object content){
        ResponseVO response = new ResponseVO();
        response.setContent(content);
        response.setCode(0);
        return response;
    }

    /**
     * OK 200
     * @param content
     * @param code
     * @return
     */
    public static ResponseVO buildOK(Object content,int code) {
        ResponseVO response = new ResponseVO();
        response.setContent(content);
        response.setCode(code);
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

    /**
     * 尝试对message进行编码
     * @param message
     * @return
     */
    private static String tryEncodeMessage(String message) {
        String encodeMessage;
        try {
            encodeMessage = URLEncoder.encode(message,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            encodeMessage = message;
        }
        return encodeMessage;
    }

}
