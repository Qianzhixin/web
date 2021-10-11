package team.ifp.cbirc.vo;

import lombok.Data;
import team.ifp.cbirc.exception.BadRequestException;

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
     */
    public static void buildBadRequest(String message) {
        throw new BadRequestException(message);
    }

}
