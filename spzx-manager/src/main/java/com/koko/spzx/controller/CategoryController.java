package com.koko.spzx.controller;

import com.alibaba.excel.EasyExcel;
import com.koko.spzx.model.entity.product.Category;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    /* 根据parentId，获取子分类列表 */
    @GetMapping("/findByParentId/{parentId}")
    public Result findCategoriesByParentId(@PathVariable Long parentId) {
        List<Category> categoryList = service.findCategoriesByParentId(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    /* 导出表格数据 */
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        service.exportData(response);
    }

    /* 导入表格数据 */
    @PostMapping("/importData")
    public Result importData(MultipartFile file) {
        service.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
