package team.ifp.cbirc.vo;

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
public class UserVO {

    String username;

    String password;

    String name;

}
