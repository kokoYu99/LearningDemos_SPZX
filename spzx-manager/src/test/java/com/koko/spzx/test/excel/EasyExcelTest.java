package com.koko.spzx.test.excel;

import com.alibaba.excel.EasyExcel;
import com.koko.spzx.model.vo.product.CategoryExcelVo;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EasyExcelTest {

    public static void main(String[] args) {
//        write();
//        read();
//        readMy();
        writeMy();
    }

    public static void readMy() {
        //指定要读取的文件名称
        String fileName = "C://Users//Lenovo//Desktop//test.xlsx";

        //创建监听器
        MyExcelListener<CategoryExcelVo> excelListener = new MyExcelListener<>();

        //进行读取
        EasyExcel.read(fileName, CategoryExcelVo.class, excelListener)
                .sheet()
                .doRead();

        //获取解析后的数据集合
        List<CategoryExcelVo> data = excelListener.getData();

        //遍历
        data.forEach(System.out::println);
    }

    public static void read() {
        //指定excel文件的路径和名称
        String fileName = "C://Users//Lenovo//Desktop//test.xlsx";

        EasyExcel.read(
                        fileName, CategoryExcelVo.class, new ExcelListener<CategoryExcelVo>()
                )
                .sheet()
                .doRead();
    }


    public static void write() {
        //准备list集合数据
        List<CategoryExcelVo> list = new ArrayList<>();
        list.add(new CategoryExcelVo(11L, "数码办公11", "", 0L, 1, 1));
        list.add(new CategoryExcelVo(111L, "华为手机11", "", 1L, 1, 2));

        //指定excel文件的路径和名称
        String fileName = "C://Users//Lenovo//Desktop//test.xlsx";

        //调用方法，实现写出
        EasyExcel.write(fileName, CategoryExcelVo.class) //文件名，实体类.class
                .sheet("test_sheet") //写出到哪个sheet
                .doWrite(list); //将集合写出到表格

    }

    public static void writeMy(){
        //准备list集合数据
        List<CategoryExcelVo> list = new ArrayList<>();
        list.add(new CategoryExcelVo(1L, "成人玩具", "", 0L, 1, 1));
        list.add(new CategoryExcelVo(11L, "紫色心情", "", 1L, 1, 2));
        list.add(new CategoryExcelVo(12L, "大人糖", "", 1L, 1, 2));
        list.add(new CategoryExcelVo(13L, "豆豆鸟", "", 1L, 1, 2));
        list.add(new CategoryExcelVo(14L, "司沃康", "", 1L, 1, 2));

        //指定写出的文件名称和路径
        String fileName = "C://Users//Lenovo//Desktop//toys.xlsx";

        //执行写出
        EasyExcel.write(fileName,CategoryExcelVo.class).sheet("sheet1").doWrite(list);
    }
}
