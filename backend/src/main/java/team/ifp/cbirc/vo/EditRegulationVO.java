package team.ifp.cbirc.vo;

import lombok.Data;
import team.ifp.cbirc.po.ExternalRegulation;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/13
 */

@Data
public class EditRegulationVO {

    private Integer id;

    private String title;

    private String number;

    private String type;

    private String publishingDepartment;

    private Integer effectivenessLevel;

    private Date releaseDate;

    private Date implementationDate;

    private String interpretationDepartment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getInterpretationDepartment() {
        return interpretationDepartment;
    }

    public void setInterpretationDepartment(String interpretationDepartment) {
        this.interpretationDepartment = interpretationDepartment;
    }

    /**
     * 检查修改属性是否全为空
     * @return
     */
    public boolean isAllNull() {
        return getTitle()==null &&
                getNumber()==null &&
                getType()==null &&
                getPublishingDepartment()==null &&
                getEffectivenessLevel()==null &&
                getReleaseDate()==null &&
                getImplementationDate()==null &&
                getInterpretationDepartment()==null;
    }

    /**
     * 根据 this 修改 ExternalRegulation 实体对象需要修改的部分
     * @param er
     * @return 返回修改过信息的对象
     */
    public ExternalRegulation fillEditProperty(ExternalRegulation er) {
        if(getTitle()!=null)er.setTitle(getTitle());
        if(getNumber()!=null)er.setNumber(getNumber());
        if(getType()!=null)er.setType(getType());
        if(getPublishingDepartment()!=null)er.setPublishingDepartment(getPublishingDepartment());
        if(getEffectivenessLevel()!=null)er.setEffectivenessLevel(getEffectivenessLevel());
        if(getReleaseDate()!=null)er.setReleaseDate(getReleaseDate());
        if(getImplementationDate()!=null)er.setImplementationDate(getImplementationDate());
        if(getInterpretationDepartment()!=null)er.setInterpretationDepartment(getInterpretationDepartment());
        return er;
    }

}
