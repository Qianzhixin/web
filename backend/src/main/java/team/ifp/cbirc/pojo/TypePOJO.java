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
public class TypePOJO {

    private int id;

    private String uri;

    private String label;

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

}
