package team.ifp.cbirc.vo;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author GuoXinyuan
 * @date 2021/10/31
 */

@Data
public class StatisticsVO {

    public StatisticsVO() {
        countPerDepartment = new HashMap<>();
        countPerType = new HashMap<>();
        countPerReleaseDate = new HashMap<>();
        countPerImplementationDate = new HashMap<>();
    }

    Map<String,Integer> countPerDepartment;

    Map<String,Integer> countPerType;

    Map<String,Integer> countPerReleaseDate;

    Map<String,Integer> countPerImplementationDate;

}
