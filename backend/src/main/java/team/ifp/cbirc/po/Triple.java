package team.ifp.cbirc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.ifp.cbirc._enum.Predicate;

import javax.persistence.*;

/**
 * @author GuoXinyuan
 * @date 2021/12/9
 */
@Entity
@Table(name = "triple")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Triple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "s_id")
    private Integer sourceId;

    @Column(name = "s_title")
    private String sourceTitle;

    @Column(name = "t_id")
    private Integer targetId;

    @Column(name = "t_title")
    private String targetTitle;

    @Column(name = "predicate")
    @Enumerated(EnumType.ORDINAL)
    private Predicate predicate;

}
