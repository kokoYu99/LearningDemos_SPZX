package com.koko.spzx.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.koko.spzx.mapper.CategoryMapper;
import com.koko.spzx.model.entity.product.Category;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.slf4j.SLF4JLogger;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;

/* 功能：
使用EasyExcel，逐行解析用户上传的表格中的CategoryExcelVo对象，
将其转换为Category对象，
并写入数据库 (使用缓存数组，提高写入效率)
*/

@Slf4j
public class MyCategoryListener<CategoryExcelVo> implements ReadListener<CategoryExcelVo> {

    private final CategoryMapper categoryMapper;

    public MyCategoryListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    //达到10条，再批量存入，节省性能开销
    private static final Integer BATCH_COUNT = 10;

    //缓存数组，存储分类对象。到达缓存临界值，就交由mapper进行存储
    private List<Category> cacheList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /*
     * 每次解析一条数据，都执行此方法
     * */
    @Override
    public void invoke(CategoryExcelVo categoryExcelVo, AnalysisContext analysisContext) {
        //1. 将vo对象转换为分类对象，放到空集合中
        Category category = new Category();
        BeanUtils.copyProperties(categoryExcelVo, category);
        cacheList.add(category);

        //2. 如果集合中的对象数量到达batchCount，就开始批量保存
        if (cacheList.size() >= BATCH_COUNT) {
            //存储vo对象
            saveCategoryData();
            //清空缓存数组
            cacheList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }


    /*
     * 全部操作完毕后，执行此方法
     * */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //最终数组中可能有未达临界值数量的vo对象。将它们也存入数据库
        saveCategoryData();
        log.info("全部数据解析完毕！");
    }

    //保存分类数据到数据库
    private void saveCategoryData() {
        log.info("{}条数据，开始存储到数据库", cacheList.size());
        int i = categoryMapper.batchInsertCategories(cacheList);
        log.info("成功存入数据库");
    }
}
