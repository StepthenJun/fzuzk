package zk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://59.77.134.2:50035")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
