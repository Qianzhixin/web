package team.ifp.cbirc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.ifp.cbirc.bl.KGService;
import team.ifp.cbirc.util.aop.OperLog;
import team.ifp.cbirc.vo.ResponseVO;

/**
 * @author GuoXinyuan
 * @date 2021/12/9
 */

@Tag(name = "知识图谱接口", description = "知识图谱相关接口")
@RestController
@RequestMapping("/kg")
public class KGController {

    @Autowired
    KGService kgService;

    /**
     * 获取某个外规相关图谱
     * @return
     */
    @Operation(summary = "外规管理-图谱", description = "获取某个外规相关图谱")
    @GetMapping("/kgInfo")
    @OperLog(operModul = "外规管理-图谱", operType = "图谱")
    ResponseEntity<ResponseVO> getKGInfo(@RequestParam("id") int id) {
        return kgService.getKGInfo(id);
    }

}
