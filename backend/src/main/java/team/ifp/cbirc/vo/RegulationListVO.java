package team.ifp.cbirc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 * 搜索法规集合VO
 */

@Data
@AllArgsConstructor
public class RegulationListVO {

    List<RegulationVO> regulationVOList;

}
