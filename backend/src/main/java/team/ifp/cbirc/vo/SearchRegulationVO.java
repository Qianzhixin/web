package team.ifp.cbirc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.ifp.cbirc._enum.RegulationState;

import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRegulationVO {

    private String title;

    private String number;

    private String publishingDepartment;

    private Integer effectivenessLevel;

    private Date releaseDate;

    private Date implementationDate;

    private RegulationState state;

    private Integer begin;

    private Integer len;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPublishingDepartment() {
        return publishingDepartment;
    }

    public void setPublishingDepartment(String publishingDepartment) {
        this.publishingDepartment = publishingDepartment;
    }

    public Integer getEffectivenessLevel() {
        return effectivenessLevel;
    }

    public void setEffectivenessLevel(Integer effectivenessLevel) {
        this.effectivenessLevel = effectivenessLevel;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getImplementationDate() {
        return implementationDate;
    }

    public void setImplementationDate(Date implementationDate) {
        this.implementationDate = implementationDate;
    }

    public RegulationState getState() {
        return state;
    }

    public void setState(RegulationState state) {
        this.state = state;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    /**
     * @return 判断内容都为空返回 true,否则返回 false
     */
    public boolean isAllNull() {
        return title == null &&
                number == null &&
                publishingDepartment == null &&
                effectivenessLevel == null &&
                releaseDate == null &&
                implementationDate == null &&
                state == null;
    }

}
