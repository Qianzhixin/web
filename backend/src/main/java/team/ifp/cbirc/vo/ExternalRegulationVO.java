package team.ifp.cbirc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.util.FileUtil;

import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 * 法规实体完整VO
 */

@Data
@AllArgsConstructor
public class ExternalRegulationVO {

    public ExternalRegulationVO(ExternalRegulation externalRegulation) {
        this.title = externalRegulation.getTitle();
        this.number = externalRegulation.getNumber();
        this.type = externalRegulation.getType();
        this.publishingDepartment = externalRegulation.getPublishingDepartment();
        this.effectivenessLevel = externalRegulation.getEffectivenessLevel();
        this.releaseDate = externalRegulation.getReleaseDate();
        this.implementationDate = externalRegulation.getImplementationDate();
        this.interpretationDepartment = externalRegulation.getInterpretationDepartment();
        this.inputPerson = externalRegulation.getInputPerson();
        this.inputDate = externalRegulation.getInputDate();
        this.text = FileUtil.readFile(externalRegulation.getTextPath()); //读取文件内容
        this.state = externalRegulation.getState();
    }

    private String title;

    private String number;

    private String type;

    private String publishingDepartment;

    private int effectivenessLevel;

    private Date releaseDate;

    private Date implementationDate;

    private String interpretationDepartment;

    private String inputPerson;

    private Date inputDate;

    private String text;

    private RegulationState state;

}
