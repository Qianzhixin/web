package team.ifp.cbirc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.ifp.cbirc._enum.RegulationState;

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

    @Column(name = "input_person")
    private String inputPerson;

    @Column(name = "input_date")
    private Date inputDate;

    @Column(name = "text_path")
    private String textPath;

    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    private RegulationState state;

}
