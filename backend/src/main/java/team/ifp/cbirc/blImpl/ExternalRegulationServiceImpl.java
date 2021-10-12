package team.ifp.cbirc.blImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.ifp.cbirc.bl.ExternalRegulationService;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationRepository;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.pojo.SearchRegulationPOJO;
import team.ifp.cbirc.vo.ExternalRegulationVO;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.SearchRegulationVO;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author GuoXinyuan
 * @date 2021/10/10
 */
@Service
public class ExternalRegulationServiceImpl implements ExternalRegulationService {

    @Autowired
    private ExternalRegulationRepository externalRegulationRepository;

    @Override
    public ResponseVO search(SearchRegulationVO vo) {
        if(vo.isAllNull()) {
            ResponseVO.buildBadRequest("所有查询条件都为空");
        }
        if(vo.getBegin()==null && vo.getLen()==null) {
            //设置为查询所有
            vo.setBegin(0);
            vo.setLen(0);
        }
        else if(vo.getBegin()==null) {
            ResponseVO.buildBadRequest("只给出了查询长度,没有给出查询起点");
        }
        else if(vo.getLen()==null) {
            ResponseVO.buildBadRequest("只给出了查询起点,没有给出查询长度");
        }
        else if(vo.getBegin()<0 || vo.getLen()<=0) {
            ResponseVO.buildBadRequest("无意义查询(查询起点: " + vo.getBegin() + ",查询长度: " + vo.getLen() + ")");
        }

        SearchRegulationPOJO searchRegulationPOJO = new SearchRegulationPOJO(vo);

        //查询获取实体列表
        List<ExternalRegulation> entityList = externalRegulationRepository.search(searchRegulationPOJO);

        if(entityList == null) {
            ResponseVO.buildInternetServerError("服务器异常");
        }

        //将实体映射为VO
        List<ExternalRegulationVO> resultList = entityList.parallelStream().map(ExternalRegulationVO::new).collect(Collectors.toList());

        return ResponseVO.buildOK(resultList);
    }
}
