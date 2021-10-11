package team.ifp.cbirc.bl;

import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.UserVO;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */
public interface IdentifyService {

    /**
     * 用户登录
     * @param userVO
     */
    ResponseVO login(UserVO userVO);

    /**
     * 用户登出
     */
    ResponseVO logout();

    /**
     * 用户注册
     * @param userVO
     */
    ResponseVO register(UserVO userVO);

}
