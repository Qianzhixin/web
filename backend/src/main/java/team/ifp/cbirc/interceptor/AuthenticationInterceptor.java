package team.ifp.cbirc.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import team.ifp.cbirc.userdata.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 * 身份认证拦截器
 */

public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求查看是否进行了登录
     * 否则401请求失败
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //放行OPTIONS预检请求
        if(request.getMethod().equalsIgnoreCase("OPTIONS")) return true;

        //阻止未登录请求
        Object user = request.getSession().getAttribute(UserSession.USER_INFO);
        if(user == null){
            response.setStatus(401);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",401);
            jsonObject.put("timestamp",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jsonObject.put("message","尚未登录");
            String jsonString = JSONObject.toJSONString(jsonObject,true);
            response.getWriter().write(jsonString);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView){
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
    }

}
