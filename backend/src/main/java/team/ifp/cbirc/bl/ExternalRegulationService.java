package team.ifp.cbirc.bl;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.ifp.cbirc.vo.*;

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
    ResponseEntity<ResponseVO> search(SearchRegulationVO searchRegulationVO);

    /**
     * 根据id下载法规正文文件
     * @param id
     * @return
     */
    ResponseEntity<InputStreamResource> downloadFile(int id);

    /**
     * 根据所给定的信息创建外规记录
     * @param file
     * @param createRegulationVO
     * @return
     */
    ResponseEntity<ResponseVO> create(MultipartFile file, CreateRegulationVO createRegulationVO);

    /**
     * 根据给定的文件与信息修改法规信息
     * @param file 法规正文文件
     *             为 null 则不对正文做出修改;
     *             否则删除原文件,替换为这次上传的文件；
     * @param editRegulationVO 法规信息修改对象
     *                 editRegulationVO==null,不对正文信息做出任何修改;
     *                 editRegulationVO 内部属性为 null,不对该属性做出修改
     * @return
     */
    ResponseEntity<ResponseVO> edit(MultipartFile file, EditRegulationVO editRegulationVO);

}
