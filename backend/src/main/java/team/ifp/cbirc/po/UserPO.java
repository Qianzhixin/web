package team.ifp.cbirc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPO {

    int id;

    String username;

    String password;

}
