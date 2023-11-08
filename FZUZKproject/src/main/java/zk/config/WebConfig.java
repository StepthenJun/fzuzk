package zk.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import zk.intercepeter.JWTInterceptors;

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
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
//    添加拦截器jwt令牌登入用的
/*@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new JWTInterceptors())
            .addPathPatterns("/FZUZK/**")  // 其他接口token验证
            .excludePathPatterns("/login");  // 所有用户都放行
}*/

/*    @Bean
    public FilterRegistrationBean<JwtFilter> loggingFilter(JwtFilter jwtFilter) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/FZUZK/*"); // 设置过滤器的URL模式
    return registrationBean;
}*/
}
