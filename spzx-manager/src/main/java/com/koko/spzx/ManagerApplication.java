package com.koko.spzx;

import com.koko.spzx.properties.AuthContextProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan({"com.koko.spzx.mapper"}) //和mapper接口的@Mapper 择一即可
@EnableConfigurationProperties(value = {AuthContextProperties.class}) //启用配置读取类，此类可以从配置文件中读取参数值
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

}
