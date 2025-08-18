package com.koko.spzx.config;

import com.koko.spzx.interceptor.AuthContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthContextInterceptor authContextInterceptor;

    /* 注册拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authContextInterceptor) //添加指定拦截器
                .excludePathPatterns("admin/system/index/generateValidateCode", "admin/system/index/login") //指定要排除的路径
                .addPathPatterns("/**"); //指定要拦截的路径
    }

    /* CORS跨域设置 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //声明哪些接口路径需要支持跨域访问
                .allowedOriginPatterns("http://localhost:*") //允许的请求来源（支持通配符 * 和 : 端口匹配）
                .allowedMethods("*") //允许的 HTTP 方法（如 GET, POST）
                .allowedHeaders("*") //允许所有请求头允许的请求头（如 Content-Type, Authorization）
                .allowCredentials(true) //允许发送cookie等凭证信息
                .maxAge(1800); //预检请求(OPTIONS)响应的缓存时间(秒)，即后续多久时间内不用重复预检
    }
}
