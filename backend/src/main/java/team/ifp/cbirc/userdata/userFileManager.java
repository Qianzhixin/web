package team.ifp.cbirc.userdata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author GuoXinyuan
 * @date 2021/10/12
 */

@Component
public class userFileManager {

    @Value("${myconfig.data-root-path}")
    private String DATA_ROOT_PATH;



}
