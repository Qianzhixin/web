package team.ifp.cbirc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.ifp.cbirc.bl.ExternalRegulationService;
import team.ifp.cbirc.vo.*;
import team.ifp.cbirc.util.aop.OperLog;
import team.ifp.cbirc.vo.CreateRegulationVO;
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
    @OperLog(operModul = "外规管理-查询", operType = "查询")
    ResponseEntity<ResponseVO> search(@RequestBody SearchRegulationVO searchRegulationVO) {
        return externalRegulationService.search(searchRegulationVO);
    }

    /**
     * 根据法规 id 获取法规文件
     * @param id
     * @return
     */
    @GetMapping("/downloadFile")
    @OperLog(operModul = "外规管理-正文下载", operType = "下载")
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
    @OperLog(operModul = "外规管理-创建外规记录", operType = "创建")
    ResponseEntity<ResponseVO> create(@RequestParam("file")MultipartFile file,@RequestParam("info")String jsonInfo) {
        //解析 json 对象
        CreateRegulationVO createRegulationVO;
        try {
            createRegulationVO = JSON.parseObject(jsonInfo,CreateRegulationVO.class);
        } catch (JSONException e) {
            e.printStackTrace();
            createRegulationVO = null;
        }
        if(createRegulationVO == null) {
            ResponseVO.buildBadRequest("无法解析所发送的 json 数据");
        }
        return externalRegulationService.create(file,createRegulationVO);
    }

    /**
     * 根据给定的文件与信息修改法规信息
     * @param file 法规正文文件
     *             为 null 则不对正文做出修改;
     *             否则删除原文件,替换为这次上传的文件；
     * @param jsonInfo 法规信息 json 对象
     *                 jsonInfo==null,不对正文信息做出任何修改;
     *                 jsonInfo 内部属性为 null,不对该属性做出修改
     * @return
     */
    @PostMapping("/edit")
    ResponseEntity<ResponseVO> edit(@RequestParam("file")MultipartFile file,@RequestParam("info")String jsonInfo) {
        //解析 json 对象
        EditRegulationVO editRegulationVO = null;
        if(jsonInfo != null) {
            try {
                editRegulationVO = JSON.parseObject(jsonInfo,EditRegulationVO.class);
            } catch (JSONException e) {
                e.printStackTrace();
                editRegulationVO = null;
            }
            if(editRegulationVO == null) {
                ResponseVO.buildBadRequest("无法解析所发送的 json 数据");
            }
        }
        return externalRegulationService.edit(file,editRegulationVO);
    }

    /**
     * 仅对法规信息做出修改
     * @param editRegulationVO
     * @return
     */
    @PostMapping("/editInfo")
    ResponseEntity<ResponseVO> edit(@RequestBody EditRegulationVO editRegulationVO) {
        return externalRegulationService.edit(null,editRegulationVO);
    }

}
