package com.koko.spzx.service.impl;

import com.alibaba.excel.EasyExcel;
import com.koko.spzx.exception.BusinessException;
import com.koko.spzx.listener.MyCategoryListener;
import com.koko.spzx.mapper.CategoryMapper;
import com.koko.spzx.model.entity.product.Category;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.model.vo.product.CategoryExcelVo;
import com.koko.spzx.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j //生成slf4j日志对象
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryMapper mapper) {
        this.mapper = mapper;
    }


    /* 根据parentId 查询所有子分类 */
    @Override
    public List<Category> findCategoriesByParentId(Long parentId) {

        //根据parentId获取分类集合
        List<Category> categoryList = mapper.findCategoriesByParentId(parentId);

        //遍历分类集合
        categoryList.forEach(category -> {
            //查询该分类下子分类的数量
            int count = mapper.countCategoriesByParentId(category.getId());
            //根据数量是否大于0，设置该分类的hasChildren值。
            category.setHasChildren(count > 0);
        });

        return categoryList;
    }

    /* 导出分类数据文件 */
    @Override
    public void exportData(HttpServletResponse response) {

        /* 1. 获取数据库的数据 */
        List<Category> categoryList = mapper.findAllCategories();


        /* 2. 处理数据，转换为要导出的目标格式(Vo对象) */
        //2.1 创建空的vo集合，存储处理好的数据
        List<CategoryExcelVo> categoryExcelVos = new ArrayList<>();

        //2.2 遍历查询到的数据，逐个处理为vo对象，并放入集合
        categoryList.forEach(category -> {
            //创建新的vo对象
            CategoryExcelVo vo = new CategoryExcelVo();
            //将数据库的分类对象复制给vo对象。使用Spring的工具类BeanUtils
            BeanUtils.copyProperties(category, vo);
            //将vo对象放入vo集合
            categoryExcelVos.add(vo);
        });

        try {
//             3. 设置响应结果的类型、响应头等

            //3.1 设置响应结果类型(MIME类型; 字符集)
            //MIME类型，是浏览器认识的文件格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8"); //xlsx格式对应的MIME类型; 字符集

            //3.2 构建Content-Disposition，并设置为响应头(必须!!!)
            //方式1. 使用spring的工具类ContentDisposition
            String fileName = "我的表格.xlsx";
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileName, StandardCharsets.UTF_8).build();//文件名称如果包含中文，需要指定编码格式，防止下载时乱码
            response.setHeader("Content-Disposition", contentDisposition.toString());

            //方式2. 使用Java的URLEncoder
//            String fileName1 = URLEncoder.encode("我的表格", "UTF-8").replaceAll("\\+", "%20");
//            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName1 + ".xlsx");


            /*4. 获取响应输出流，使用EasyExcel工具将数据写出 */
            //注：输出流无需手动关闭，Spring会管理
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据")
                    .doWrite(categoryExcelVos);

        } catch (IOException e) {
            //打印日志错误信息
            log.error("导出Excel失败: {}", e.getMessage(), e);
            throw new BusinessException(ResultCodeEnum.DATA_ERROR);
        }

    }

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, new MyCategoryListener(mapper))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            log.error("导入Excel失败: {}", e.getMessage(), e);
            throw new BusinessException(ResultCodeEnum.DATA_ERROR);
        }
    }


}

