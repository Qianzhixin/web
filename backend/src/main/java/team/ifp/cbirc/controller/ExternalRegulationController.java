package team.ifp.cbirc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.ifp.cbirc.bl.ExternalRegulationService;
import team.ifp.cbirc.vo.CreateRegulationVO;
import team.ifp.cbirc.vo.ExternalRegulationVO;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.SearchRegulationVO;

import javax.websocket.server.PathParam;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

@RestController
@RequestMapping("/externalRegulation")
public class ExternalRegulationController {

    @Autowired
    private ExternalRegulationService externalRegulationService;

    /**
     * 根据 searchRegulationVO 中的信息搜索满足条件的法规
     * 当条件为 null 则不参与搜索;
     * 如果全为 null 则返回空集;
     * 如果查询范围(begin,len) 都为 null 则查询全部;
     * @param searchRegulationVO 查询条件集合类
     * @return
     */
    @PostMapping("/search")
    ResponseEntity<ResponseVO> search(@RequestBody SearchRegulationVO searchRegulationVO) {
        return externalRegulationService.search(searchRegulationVO);
    }

    /**
     * 根据法规 id 获取法规文件
     * @param id
     * @return
     */
    @GetMapping("/downloadFile")
    ResponseEntity<InputStreamResource> downloadFile(@PathParam("id") int id) {
        return externalRegulationService.downloadFile(id);
    }

    /**
     * 根据所给定的信息创建外规记录
     * @param file
     * @param jsonInfo
     * @return
     */
    @PostMapping("/create")
    ResponseEntity<ResponseVO> create(@RequestParam("file")MultipartFile file,@RequestParam("info")String jsonInfo) {
        CreateRegulationVO createRegulationVO = JSON.parseObject(jsonInfo,CreateRegulationVO.class);
        return externalRegulationService.create(file,createRegulationVO);
    }

}
