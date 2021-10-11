package team.ifp.cbirc.dao.externalRegulation;

import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.entity.ExternalRegulation;
import team.ifp.cbirc.po.SearchRegulationPO;

import java.util.Date;
import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/10/10
 */

public interface ExternalRegulationDao {


    /**
     * 根据 searchRegulationVO 中的信息搜索满足条件的法规
     * 当条件为 null 则不参与搜索
     * 当全为 null 时返回空值
     * @param po 搜索条件集合类
     * @return
     */
    public List<ExternalRegulation> search(SearchRegulationPO po);

}
