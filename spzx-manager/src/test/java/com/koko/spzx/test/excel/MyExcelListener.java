package com.koko.spzx.test.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.koko.spzx.model.vo.product.CategoryExcelVo;

import java.util.ArrayList;
import java.util.List;

public class MyExcelListener<CategoryExcelVo> implements ReadListener<CategoryExcelVo> {

    //创建空集合，存储解析好的数据
    private List<CategoryExcelVo> data = new ArrayList<>();

    //获取数据集合的公共方法
    public List<CategoryExcelVo> getData(){
        return data;
    }

    /* 每解析一行，执行一次 */
    @Override
    public void invoke(CategoryExcelVo categoryExcelVo, AnalysisContext analysisContext) {
        //每次读取，都将数据添加到集合中
        data.add(categoryExcelVo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("数据读取完毕......");
    }
}
