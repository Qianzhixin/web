package team.ifp.cbirc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.ifp.cbirc.pojo.LinkPOJO;
import team.ifp.cbirc.pojo.NodePOJO;
import team.ifp.cbirc.pojo.TypePOJO;

import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/12/9
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KGVO {

    private int centerId; //核心法规id

    private List<NodePOJO> nodes;

    private List<LinkPOJO> links;

    private List<TypePOJO> types;

}
