package team.ifp.cbirc.bl;

import org.springframework.http.ResponseEntity;
import team.ifp.cbirc.vo.ResponseVO;

/**
 * @author GuoXinyuan
 * @date 2021/12/9
 */
public interface KGService {

    /**
     * 获取id所指外规相关知识图谱信息
     * @param id
     * @return
     */
    ResponseEntity<ResponseVO> getKGInfo(Integer id);

}
