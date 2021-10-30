package team.ifp.cbirc.jaxws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.ifp.cbirc.bl.ExternalRegulationService;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationRepository;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.pojo.SearchRegulationPOJO;
import team.ifp.cbirc.util.aop.OperLog;
import team.ifp.cbirc.vo.SearchRegulationVO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LawLibraryServiceImpl implements LawLibraryService{

    @Autowired
    ExternalRegulationService externalRegulationService;

    @Autowired
    private ExternalRegulationRepository externalRegulationRepository;

    @Autowired
    ServerConfig serverConfig;

    @OperLog(operModul = "WebService-数据访问", operType = "查询")
    @Override
    public DataAccessResponse getExternalRegulationList(SearchRegulationVO searchRegulationVO) {
        return search(searchRegulationVO);
    }

    public DataAccessResponse search(SearchRegulationVO vo) {
        if(vo.isAllNull()) {
            // 无过滤条件，查询全部
            List<ExternalRegulation> entityList = externalRegulationRepository.findAll();
            List<TargetExternalRegulation> resultList = entityList.parallelStream().map(TargetExternalRegulation::new).collect(Collectors.toList());
            for(TargetExternalRegulation ter: resultList){
                // 拼接静态资源url
                ter.setTextPath(serverConfig.getUrl() + "/static/" + ter.getTextPath());
            }
            return DataAccessResponse.buildOK(resultList.subList(0, 10));
        }
        if(vo.getBegin()==null && vo.getLen()==null) {
            vo.setBegin(0);
            vo.setLen(0);
        }
        else if(vo.getBegin()==null) {
            return DataAccessResponse.buildBadRequest("只给出了查询长度,没有给出查询起点");
        }
        else if(vo.getLen()==null) {
            return DataAccessResponse.buildBadRequest("只给出了查询起点,没有给出查询长度");
        }
        else if(vo.getBegin()<0 || vo.getLen()<=0) {
            return DataAccessResponse.buildBadRequest("无意义查询(查询起点: " + vo.getBegin() + ",查询长度: " + vo.getLen() + ")");
        }

        SearchRegulationPOJO searchRegulationPOJO = new SearchRegulationPOJO(vo);

        List<ExternalRegulation> entityList = externalRegulationRepository.search(searchRegulationPOJO);

        if(entityList == null) {
            return DataAccessResponse.buildInternetServerError("服务器异常");
        }

        List<TargetExternalRegulation> resultList = entityList.parallelStream().map(TargetExternalRegulation::new).collect(Collectors.toList());

        return DataAccessResponse.buildOK(resultList);
    }
}
