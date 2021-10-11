package team.ifp.cbirc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.ifp.cbirc.bl.ExternalRegulationService;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.SearchRegulationVO;

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
    ResponseVO search(@RequestBody SearchRegulationVO searchRegulationVO) {
        return externalRegulationService.search(searchRegulationVO);
    }

}
