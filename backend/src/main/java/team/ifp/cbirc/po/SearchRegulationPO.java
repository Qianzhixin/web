package team.ifp.cbirc.po;

import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.vo.SearchRegulationVO;

import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/10
 * 搜索法规数据PO
 */
public class SearchRegulationPO {

    public SearchRegulationPO(SearchRegulationVO searchRegulationVO) {
        this.content = searchRegulationVO;
    }

    private final SearchRegulationVO content;

    public String getTitle() {
        return content.getTitle();
    }

    public String getNumber() {
        return content.getNumber();
    }

    public String getPublishingDepartment() {
        return content.getPublishingDepartment();
    }

    public Integer getEffectivenessLevel() {
        return content.getEffectivenessLevel();
    }

    public Date getReleaseDate() {
        return content.getReleaseDate();
    }

    public Date getImplementationDate() {
        return content.getImplementationDate();
    }

    public RegulationState getState() {
        return content.getState();
    }

    public int getBegin() { return content.getBegin(); }

    public int getLen() { return content.getLen(); };

}
