package com.briup.product_source.config;

import com.briup.product_source.Interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 对swagger的请求不进行拦截
        String[] excludePatterns = new String[]{
                "/profile/**",
                "/common/download**",
                "/common/download/resource**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/*/api-docs",
                "/favicon.ico",
                "/doc.html",
                "/error"
        };
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePatterns)
                .excludePathPatterns("/login");
                //.excludePathPatterns("/animal/findByAnimalId/**");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //根据HTTP协议规定的预检请求的返回头信息进行设置
        registry.addMapping("/**")// 映射所有路径
                .allowedOriginPatterns("*")// 运行所有客户端访问
                //不允许携带cookie
                .allowCredentials(false)
                //支持的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                //运行所有请求头字段
                .allowedHeaders("*")
                //允许客户端缓存“预检请求”中获取的信息，3600秒
                .maxAge(3600);
    }
}
