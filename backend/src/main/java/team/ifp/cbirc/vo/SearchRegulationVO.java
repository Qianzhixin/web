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

    private String effectivenessLevel;

    private Date releaseDate;

    private Date implementationDate;

    private RegulationState state;

    private Integer begin;

    private Integer len;

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
