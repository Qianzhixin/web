package team.ifp.cbirc.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 * 禁止返回异常中含有 trace 属性
 */

@Component
public class AppErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, false); // 这里参数可以配置为false
//        map.put("trace","");
        return map;
    }

}
