package team.ifp.cbirc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.vo.CreateRegulationVO;
import team.ifp.cbirc.vo.EditRegulationVO;
import team.ifp.cbirc.vo.ExternalRegulationVO;

import javax.persistence.*;
import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 * external_regulation表实体类
 */
@Entity
@Table(name = "external_regulation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalRegulation {

    public ExternalRegulation(ExternalRegulationVO externalRegulationVO) {
        title = externalRegulationVO.getTitle();
        number = externalRegulationVO.getNumber();
        type = externalRegulationVO.getType();
        publishingDepartment = externalRegulationVO.getPublishingDepartment();
        effectivenessLevel = externalRegulationVO.getEffectivenessLevel();
        releaseDate = externalRegulationVO.getReleaseDate();
        implementationDate = externalRegulationVO.getImplementationDate();
        interpretationDepartment = externalRegulationVO.getInterpretationDepartment();
        user = new User();
        user.setId(externalRegulationVO.getInputPersonId());
        inputDate = externalRegulationVO.getInputDate();
        textPath = null;
        state = externalRegulationVO.getState();
    }

    public ExternalRegulation(CreateRegulationVO createRegulationVO,User user,String textPath) {
        title = createRegulationVO.getTitle();
        number = createRegulationVO.getNumber();
        type = createRegulationVO.getType();
        publishingDepartment = createRegulationVO.getPublishingDepartment();
        effectivenessLevel = createRegulationVO.getEffectivenessLevel();
        releaseDate = createRegulationVO.getReleaseDate();
        implementationDate = createRegulationVO.getImplementationDate();
        interpretationDepartment = createRegulationVO.getInterpretationDepartment();
        this.user = user;
        inputDate = new Date();
        this.textPath = textPath;
        state = RegulationState.UNPUBLISHED;
    }

    public ExternalRegulation(EditRegulationVO editRegulationVO,String textPath) {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "number")
    private String number;

    @Column(name = "type")
    private String type;

    @Column(name = "publishing_department")
    private String publishingDepartment;

    @Column(name = "effectiveness_level")
    private int effectivenessLevel;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "implementation_date")
    private Date implementationDate;

    @Column(name = "interpretation_department")
    private String interpretationDepartment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "input_person_id")
    private User user;

    @Column(name = "input_date")
    private Date inputDate;

    @Column(name = "text_path")
    private String textPath;

    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
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

    public int getEffectivenessLevel() {
        return effectivenessLevel;
    }

    public void setEffectivenessLevel(int effectivenessLevel) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public String getTextPath() {
        return textPath;
    }

    public void setTextPath(String textPath) {
        this.textPath = textPath;
    }

    public RegulationState getState() {
        return state;
    }

    public void setState(RegulationState state) {
        this.state = state;
    }
}
