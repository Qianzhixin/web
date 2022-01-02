package team.ifp.cbirc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: GuoXinyuan
 * @Date: 2021/6/30
 */
@Configuration
public class WebApplicationConfig extends WebMvcConfigurerAdapter {

    @Value("${myconfig.resource-root-path}")
    private String RESOURCE_ROOT_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(RESOURCE_ROOT_PATH);
        registry.addResourceHandler("/data/**").addResourceLocations(RESOURCE_ROOT_PATH);
        super.addResourceHandlers(registry);
    }

}
