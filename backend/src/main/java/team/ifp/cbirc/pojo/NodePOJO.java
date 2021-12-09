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
public class NodePOJO {

    private int id; //展示id 每次绘制图谱时动态生成

    private int storeId; //存储id 代表节点对应数据库中的数据

    private String uri;

    private String label;

    private int typeId;

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof NodePOJO){
            return ((NodePOJO) o).getUri().equals(this.uri);
        }
        else return false;
    }

//    @Override
//    public NodePOJO clone() {
//        return new NodePOJO(id,uri,label,typeId);
//    }

}
