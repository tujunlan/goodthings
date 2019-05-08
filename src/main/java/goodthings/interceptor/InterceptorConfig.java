package goodthings.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by tujl on 2018/12/18.
 */
@Configuration
public class InterceptorConfig implements  WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogCostInterceptor()).addPathPatterns("/**");
    }
    @Bean
    public LogCostInterceptor logCostInterceptor(){
        return new LogCostInterceptor();
    }
}
