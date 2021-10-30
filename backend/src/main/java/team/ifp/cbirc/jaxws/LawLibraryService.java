package team.ifp.cbirc.jaxws;

import org.springframework.http.ResponseEntity;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.SearchRegulationVO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "LawLibraryService",
        targetNamespace = "http://webservices.jaxws.cbirc.ifp.team")
public interface LawLibraryService {
    @WebMethod
    ResponseEntity<ResponseVO> getExternalRegulationList(@WebParam(name = "searchRegulationVO") SearchRegulationVO searchRegulationVO);
}
