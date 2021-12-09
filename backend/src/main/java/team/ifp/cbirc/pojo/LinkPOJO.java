package team.ifp.cbirc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GuoXinyuan
 * @date 2021/12/9
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkPOJO {

    private String label;

    private int sourceId;

    private int targetId;

}
