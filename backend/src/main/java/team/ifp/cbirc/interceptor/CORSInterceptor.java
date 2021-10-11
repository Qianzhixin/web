package team.ifp.cbirc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import team.ifp.cbirc.config.MainConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 * 跨域拦截器
 */

public class CORSInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //添加跨域CORS
        response.setHeader("Access-Control-Allow-Origin", MainConfig.CORS_ADDRESS);
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token,responseType");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials","true");

        return true;
    }

}
