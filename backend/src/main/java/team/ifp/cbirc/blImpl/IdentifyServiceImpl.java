package team.ifp.cbirc.blImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.ifp.cbirc.bl.IdentifyService;
import team.ifp.cbirc.dao.UserRepository;
import team.ifp.cbirc.po.User;
import team.ifp.cbirc.userdata.UserSession;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.UserVO;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */
@Service
public class IdentifyServiceImpl implements IdentifyService {

    @Autowired
    UserRepository userRepository;

    /**
     * 创建用户锁
     */
    private static final Semaphore buildUserLock = new Semaphore(1);

    @Override
    public ResponseEntity<ResponseVO> login(UserVO userVO) {
        if(userVO.getUsername() == null) {
            ResponseVO.buildBadRequest("用户名不可为空");
        }
        if(userVO.getPassword() == null) {
            ResponseVO.buildBadRequest("密码不可为空");
        }

        if(UserSession.getUser() != null) {
            ResponseVO.buildBadRequest("请勿重复登录");
        }

        List<User> list = userRepository.findByUsername(userVO.getUsername());

        if(list.size() == 0) {
            ResponseVO.buildBadRequest("用户名不存在");
        }
        if(list.size() > 1) {
            ResponseVO.buildInternetServerError("服务器异常");
        }

        User user = list.get(0);

        if(!user.getPassword().equals(userVO.getPassword())) {
            ResponseVO.buildUnauthorized("密码错误");
        }

        UserSession.loginUser(user);

        return ResponseEntity.ok(ResponseVO.buildOK("登录成功"));
    }

    @Override
    public ResponseEntity<ResponseVO> logout() {
        UserSession.logoutUser();

        return ResponseEntity.ok(ResponseVO.buildOK("注销成功"));
    }

    @Override
    public ResponseEntity<ResponseVO> register(UserVO userVO) {
        if(userVO.getUsername() == null) {
            ResponseVO.buildBadRequest("用户名不可为空");
        }
        if(userVO.getPassword() == null) {
            ResponseVO.buildBadRequest("密码不可为空");
        }
        if(userVO.getName() == null) {
            ResponseVO.buildBadRequest("姓名不可为空");
        }

        buildUserLock.acquireUninterruptibly();
        try {
            List<User> list = userRepository.findByUsername(userVO.getUsername());

            if(list.size() > 0) {
                ResponseVO.buildBadRequest("用户名已存在");
            }

            User user = new User();
            user.setUsername(userVO.getUsername());
            user.setPassword(userVO.getPassword());
            user.setUsername(userVO.getName());

            userRepository.save(user);
        } finally {
            buildUserLock.release();
        }

        return ResponseEntity.ok(ResponseVO.buildOK("注册成功"));
    }

}
