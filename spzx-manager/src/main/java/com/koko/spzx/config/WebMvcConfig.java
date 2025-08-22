package com.koko.spzx.config;

import com.koko.spzx.interceptor.AuthContextInterceptor;
import com.koko.spzx.properties.AuthContextProperties;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class WebMvcConfig implements WebMvcConfigurer {

    //注入配置读取类，包含配置文件中要排除的路径
    private final AuthContextProperties authContextProperties;
    //注入登录校验拦截器
    private final AuthContextInterceptor authContextInterceptor;

    /* 构造器注入 */
    public WebMvcConfig(AuthContextProperties authContextProperties, AuthContextInterceptor authContextInterceptor) {
        this.authContextProperties = authContextProperties;
        this.authContextInterceptor = authContextInterceptor;
    }

    /* 注册拦截器 */
/*    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authContextInterceptor) //添加指定拦截器
                .excludePathPatterns(authContextProperties.getNoAuthUrls()) //优化：使用配置参数，指定要排除的路径
//                .excludePathPatterns("/admin/system/index/login","/admin/system/index/generateValidateCode")
                .addPathPatterns("/**"); //指定要拦截的路径
    }*/

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
