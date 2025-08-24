package com.koko.spzx.controller;

import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.FileUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin/system")
public class FileUploadController {

    private final FileUploadService service;

    public FileUploadController(FileUploadService service) {
        this.service = service;
    }

    /* 接收上传的文件，并返回文件url */
    @PostMapping("/fileUpload")
    public Result<String> fileUpload(MultipartFile file) {
        /* 注：方法参数名为file，
        是因为前端的文件标签中的name属性的默认值为file，
        <input type="file" name="file" />
        与其保持一致，才能正确接收到文件
        */
        String fileUrl = service.fileUpload(file);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }
}
