package team.ifp.cbirc.bl;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.UserVO;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */
public interface IdentifyService {

    ResponseEntity<ResponseVO> currentUser();

    /**
     * 用户登录
     * @param userVO
     */
    ResponseEntity<ResponseVO> login(UserVO userVO);

    /**
     * 用户登出
     */
    ResponseEntity<ResponseVO> logout();

    /**
     * 用户注册
     * @param userVO
     */
    ResponseEntity<ResponseVO> register(UserVO userVO);

}
