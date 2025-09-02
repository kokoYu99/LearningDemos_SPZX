package com.koko.spzx.mapper;

import com.koko.spzx.model.dto.product.CategoryBrandDto;
import com.koko.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {

    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    int insertCategoryBrand(CategoryBrand categoryBrand);

    int updateCategoryBrandById(CategoryBrand categoryBrand);

    int deleteCategoryBrandById(Long id);
}
