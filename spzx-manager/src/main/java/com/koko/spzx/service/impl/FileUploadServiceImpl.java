package com.koko.spzx.service.impl;

import cn.hutool.core.date.DateUtil;
import com.koko.spzx.exception.SysUserException;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.properties.MinioProperties;
import com.koko.spzx.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    //注入配置读取类，读取minio的url、账号、密码、bucket名称
    private final MinioProperties minioProperties;

    public FileUploadServiceImpl(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Override
    public String fileUpload(MultipartFile file) {

        try {
            // 创建一个Minio的客户端对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();

            // 判断桶是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) { // 如果不存在，那么此时就创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {  // 如果存在，打印信息
                System.out.println("Bucket 'spzx-img' already exists.");
            }

            //设置文件名称: "年月日/UUID"
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //生成最终的文件名称
            String fileName = dateDir + "/" + uuid + file.getOriginalFilename();
            System.out.println(fileName);

            //构建存储的文件
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName()) //目标bucket
                    .stream(file.getInputStream(), file.getSize(), -1) //文件输入流
                    .object(fileName) //文件名称。如果传递/a/b/01.jpg，会自动创建多层目录进行存储；如果仅文件名，存在根路径下
                    .build();

            //上传文件到服务器
            minioClient.putObject(putObjectArgs);

            //构建fileUrl
            String fileUrl = minioProperties.getEndpointUrl() + "/"
                    + minioProperties.getBucketName() + "/"
                    + fileName;

            return fileUrl;

        } catch (Exception e) {
            throw new SysUserException(ResultCodeEnum.DATA_ERROR);
        }


    }
}
