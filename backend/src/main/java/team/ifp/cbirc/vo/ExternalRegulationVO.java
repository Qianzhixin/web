package team.ifp.cbirc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.po.ExternalRegulation;

import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 * 法规实体完整VO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalRegulationVO {

    public ExternalRegulationVO(ExternalRegulation externalRegulation) {
        this.id = externalRegulation.getId();
        this.title = externalRegulation.getTitle();
        this.number = externalRegulation.getNumber();
        this.type = externalRegulation.getType();
        this.publishingDepartment = externalRegulation.getPublishingDepartment();
        this.effectivenessLevel = externalRegulation.getEffectivenessLevel();
        this.releaseDate = externalRegulation.getReleaseDate();
        this.implementationDate = externalRegulation.getImplementationDate();
        this.interpretationDepartment = externalRegulation.getInterpretationDepartment();
        this.inputPersonId = externalRegulation.getUser().getId();
        this.inputPersonName = externalRegulation.getUser().getName();
        this.inputDate = externalRegulation.getInputDate();
        this.state = externalRegulation.getState();
    }

    private int id;

    private String title;

    private String number;

    private String type;

    private String publishingDepartment;

    private String effectivenessLevel;

    private String releaseDate;

    private String implementationDate;

    private String interpretationDepartment;

    private int inputPersonId;

    private String inputPersonName;

    private Date inputDate;

    private RegulationState state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getInputPersonId() {
        return inputPersonId;
    }

    public void setInputPersonId(int inputPersonId) {
        this.inputPersonId = inputPersonId;
    }

    public String getInputPersonName() {
        return inputPersonName;
    }

    public void setInputPersonName(String inputPersonName) {
        this.inputPersonName = inputPersonName;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public RegulationState getState() {
        return state;
    }

    public void setState(RegulationState state) {
        this.state = state;
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
}
