package team.ifp.cbirc.jaxws;

import team.ifp.cbirc.vo.SearchRegulationVO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "LawLibraryService",
        targetNamespace = "http://webservices.jaxws.cbirc.ifp.team")
public interface LawLibraryService {
    @WebMethod
    DataAccessResponse getExternalRegulationList(@WebParam(name = "searchRegulationVO") SearchRegulationVO searchRegulationVO);
}
