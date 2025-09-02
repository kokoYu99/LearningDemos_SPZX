package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findCategoriesByParentId(Long parentId);

    int countCategoriesByParentId(Long parentId);

    List<Category> findAllCategories();

    int batchInsertCategories(List<Category> cacheList);
}
