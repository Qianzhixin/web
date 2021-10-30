package team.ifp.cbirc.jaxws;

import org.springframework.http.ResponseEntity;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.SearchRegulationVO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "LawLibraryService",
        targetNamespace = "http://webservices.jaxws.cbirc.ifp.team")
public interface LawLibraryService {
    @WebMethod
    DataAccessResponse getExternalRegulationList(@WebParam(name = "searchRegulationVO") SearchRegulationVO searchRegulationVO);
}
