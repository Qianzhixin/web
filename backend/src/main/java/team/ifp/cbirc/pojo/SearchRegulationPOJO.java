package team.ifp.cbirc.pojo;

import lombok.Data;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.vo.SearchRegulationVO;

import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/10
 * 搜索法规数据条件对象,用于 Service 向 Dao 传递
 */
@Data
public class SearchRegulationPOJO {

    public SearchRegulationPOJO(SearchRegulationVO searchRegulationVO) {
        this.content = searchRegulationVO;
        if(this.content.getBegin() == null) this.content.setBegin(0);
        if(this.content.getLen() == null) this.content.setLen(0);
    }

    private final SearchRegulationVO content;

    private void setContent(){}

    private SearchRegulationVO getContent(){
        return null;
    }

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

    public int getLen() { return content.getLen(); }

}
