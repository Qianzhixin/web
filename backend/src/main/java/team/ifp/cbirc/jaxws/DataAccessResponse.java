package team.ifp.cbirc.jaxws;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getExternalRegulationRS")
public class DataAccessResponse {
    public DataAccessResponse(){}

    private int code;

    private String errorMessage;

    @XmlElement(name = "externalRegulation")
    private List<TargetExternalRegulation> externalRegulationList;

    /**
     * OK 200
     * @param externalRegulationList
     * @return
     */
    public static DataAccessResponse buildOK(List<TargetExternalRegulation> externalRegulationList){
        DataAccessResponse response = new DataAccessResponse();
        response.setCode(0);
        response.setExternalRegulationList(externalRegulationList);
        return response;
    }

    public static DataAccessResponse buildOK(List<TargetExternalRegulation> externalRegulationList,int code) {
        DataAccessResponse response = new DataAccessResponse();
        response.setExternalRegulationList(externalRegulationList);
        response.setCode(code);
        return response;
    }

    /**
     * Internet Server Error
     * @param message
     */
    public static DataAccessResponse buildInternetServerError(String message){
        DataAccessResponse response = new DataAccessResponse();
        response.setCode(1);
        response.setErrorMessage(message);
        return response;
    }

    /**
     * Bad Request 400
     * @param message
     */
    public static DataAccessResponse buildBadRequest(String message) {
        DataAccessResponse response = new DataAccessResponse();
        response.setCode(2);
        response.setErrorMessage(message);
        return response;
    }

    /**
     * Unauthorized 401
     * @param message
     */
    public static DataAccessResponse buildUnauthorized(String message) {
        DataAccessResponse response = new DataAccessResponse();
        response.setCode(3);
        response.setErrorMessage(message);
        return response;
    }

    /**
     * Not Found 404
     * @param message
     */
    public static DataAccessResponse buildNotFound(String message) {
        DataAccessResponse response = new DataAccessResponse();
        response.setCode(2);
        response.setErrorMessage(message);
        return response;
    }
}
