package team.ifp.cbirc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationDao;
import team.ifp.cbirc.interceptor.AuthenticationInterceptor;
import team.ifp.cbirc.interceptor.CORSInterceptor;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${myconfig.cors-address}")
    private String CORS_ADDRESS;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CORSInterceptor(CORS_ADDRESS));

        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/identify/login")
                .excludePathPatterns("/identify/register")
                .excludePathPatterns("/error")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v3/**",
                        "/swagger-ui.html/**","/swagger-ui/**","/mgr","/mgr/**","/h5","/h5/**");;
    }


}
