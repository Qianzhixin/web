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

    /**
     * 获取某一法律版本变迁
     * @param id
     * @return
     */
    ResponseEntity<ResponseVO> versionChanges(Integer id);

}
