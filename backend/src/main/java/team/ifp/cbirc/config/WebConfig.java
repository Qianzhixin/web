package team.ifp.cbirc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.ifp.cbirc.interceptor.AuthenticationInterceptor;
import team.ifp.cbirc.interceptor.CORSInterceptor;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CORSInterceptor());

        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/identify/login")
                .excludePathPatterns("/identify/register")
                .excludePathPatterns("/error")
                .excludePathPatterns("/test/**");
    }


}
