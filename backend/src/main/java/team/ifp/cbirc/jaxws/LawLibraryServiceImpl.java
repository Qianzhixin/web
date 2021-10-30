package team.ifp.cbirc.jaxws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import team.ifp.cbirc.bl.ExternalRegulationService;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.SearchRegulationVO;

@Component
public class LawLibraryServiceImpl implements LawLibraryService{

    @Autowired
    ExternalRegulationService externalRegulationService;


    @Override
    public ResponseEntity<ResponseVO> getExternalRegulationList(SearchRegulationVO searchRegulationVO) {
        return externalRegulationService.search(searchRegulationVO);
    }
}
