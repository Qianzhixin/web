package team.ifp.cbirc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegulationVO {

    private String title;

    private String number;

    private String type;

    private String publishingDepartment;

    private String effectivenessLevel;

    private String releaseDate;

    private String implementationDate;

    private String interpretationDepartment;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublishingDepartment() {
        return publishingDepartment;
    }

    public void setPublishingDepartment(String publishingDepartment) {
        this.publishingDepartment = publishingDepartment;
    }

    public String getEffectivenessLevel() {
        return effectivenessLevel;
    }

    public void setEffectivenessLevel(String effectivenessLevel) {
        this.effectivenessLevel = effectivenessLevel;
    }

    public String getInterpretationDepartment() {
        return interpretationDepartment;
    }

    public void setInterpretationDepartment(String interpretationDepartment) {
        this.interpretationDepartment = interpretationDepartment;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImplementationDate() {
        return implementationDate;
    }

    public void setImplementationDate(String implementationDate) {
        this.implementationDate = implementationDate;
    }

    /**
     * 检查输入条件合法性(必输项是否为空)
     * 如果不合法抛出 400 异常
     */
    public void checkLegalityAndThrowException() {
        if(this.getTitle() == null) {
            ResponseVO.buildBadRequest("法规标题不可为空");
        }
        if(this.getEffectivenessLevel() == null) {
            ResponseVO.buildBadRequest("效力等级不可为空");
        }
        if(this.getImplementationDate() == null) {
            ResponseVO.buildBadRequest("实施日期不可为空");
        }
        if(this.getReleaseDate() == null) {
            ResponseVO.buildBadRequest("发布日期不可为空");
        }
        if(this.getPublishingDepartment() == null) {
            ResponseVO.buildBadRequest("发布部门不可为空");
        }
        if(this.getType() == null) {
            ResponseVO.buildBadRequest("法规类型不可为空");
        }
    }

}
