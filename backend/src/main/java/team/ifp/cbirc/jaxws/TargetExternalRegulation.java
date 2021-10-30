package team.ifp.cbirc.jaxws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.vo.ExternalRegulationVO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetExternalRegulation")
public class TargetExternalRegulation {
    public TargetExternalRegulation(ExternalRegulation externalRegulation) {
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
        this.textPath = externalRegulation.getTextPath();
    }

    @XmlAttribute
    private int id;
    @XmlAttribute
    private String title;
    @XmlAttribute
    private String number;
    @XmlAttribute
    private String type;
    @XmlAttribute
    private String publishingDepartment;
    @XmlAttribute
    private String effectivenessLevel;
    @XmlAttribute
    private String releaseDate;
    @XmlAttribute
    private String implementationDate;
    @XmlAttribute
    private String interpretationDepartment;
    @XmlAttribute
    private int inputPersonId;
    @XmlAttribute
    private String inputPersonName;
    @XmlAttribute
    private Date inputDate;
    @XmlAttribute
    private String textPath;  // 后端静态资源路径url
    @XmlAttribute
    private RegulationState state;

}
