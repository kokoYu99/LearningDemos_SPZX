package com.koko.spzx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix="spzx.auth") //前缀不能驼峰命名
public class AuthContextProperties {
    private List<String> noAuthUrls;
}
