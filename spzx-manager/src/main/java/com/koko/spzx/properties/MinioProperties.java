package com.koko.spzx.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spzx.minio") //要去启动类的@EnableConfigurationProperties注册一下！！！
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secretKey;
    private String bucketName;

/*    endpointUrl: http://127.0.0.1:9000
    accessKey: admin
    secretKey: admin123456
    bucketName: spzx-img*/
}
