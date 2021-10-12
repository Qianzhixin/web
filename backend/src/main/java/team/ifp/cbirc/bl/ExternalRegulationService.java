package team.ifp.cbirc.bl;

import org.springframework.web.multipart.MultipartFile;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.SearchRegulationVO;

/**
 * @author GuoXinyuan
 * @date 2021/10/10
 */
public interface ExternalRegulationService {

    /**
     * 根据searchRegulationVO中的信息搜索满足条件的法规
     * 当条件为null则不参与搜索;
     * 如果全为null则返回空集;
     * 如果查询范围(begin,len) 都为 null 则查询全部;
     * @param searchRegulationVO
     * @return
     */
    ResponseVO search(SearchRegulationVO searchRegulationVO);


}
