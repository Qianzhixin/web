package team.ifp.cbirc.userdata;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import team.ifp.cbirc.po.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 * 封装用户登录Session存储相关逻辑
 */
public class UserSession {

    /**
     * 用户信息存放键
     */
    public static final String USER_INFO = "ui";

    /**
     * 用户登录
     */
    public static void loginUser(User user) {
        getHttpSession().setAttribute(USER_INFO,user);
    }

    /**
     * 用户登出
     */
    public static void logoutUser() {
        HttpSession session = getHttpSession();
        session.removeAttribute(USER_INFO);
        session.invalidate();
    }

    /**
     * 获取当前登入的用户
     * @return
     */
    public static User getUser() {
        User user = (User) getHttpSession().getAttribute(USER_INFO);
        if(user == null) return null;
        User cloneUser = new User();
        cloneUser.setId(user.getId());
        cloneUser.setUsername(user.getUsername());
        cloneUser.setPassword(user.getPassword());
        return cloneUser;
    }

    /**
     * 获取当前 httpSession 对象
     * @return
     */
    public static HttpSession getHttpSession(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        return request.getSession();
    }

}
