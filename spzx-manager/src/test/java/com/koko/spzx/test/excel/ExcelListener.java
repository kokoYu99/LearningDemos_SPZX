package com.koko.spzx.test.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

import java.util.Date;

/* 创建一个监听器类，实现ReadListener接口的方法 */
public class ExcelListener<CategoryExcelVo> implements ReadListener<CategoryExcelVo> {

    /*
     * 以下2个方法必须实现，其他方法可选
     * */

    /* 每解析一行，就执行一次此方法
     * 将解析的每行内容封装到参数中
     * 注意：从表格的第二行开始解析(默认第一行是表头)
     *  */
    @Override
    public void invoke(CategoryExcelVo categoryExcelVo, AnalysisContext analysisContext) {
        //打印读取到的内容，测试是否从第二行开始读
        System.out.println(new Date() + " : " + categoryExcelVo.toString());
    }

    /*
    * 所有操作完成后，会执行此方法
    * */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("Excel读取操作执行完毕......");
    }
}
