package team.ifp.cbirc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/12/10
 */

@Data
@NoArgsConstructor
public class VersionChangesVO {

    private List<ChangeUnit> changes = new LinkedList<>();

    public void addChanges(Date date,String event,Integer sourceId,String source) {
        ChangeUnit changeUnit = new ChangeUnit(date,event,sourceId,source);
        changes.add(changeUnit);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ChangeUnit {

        Date date;

        String event;

        Integer sourceId;

        String source; //废止来源

    }

}
