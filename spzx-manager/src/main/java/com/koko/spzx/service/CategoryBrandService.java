package com.koko.spzx.service;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.product.CategoryBrandDto;
import com.koko.spzx.model.entity.product.Brand;
import com.koko.spzx.model.entity.product.CategoryBrand;

import java.util.List;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto);

    void insertCategoryBrand(CategoryBrand categoryBrand);

    void updateCategoryBrandById(CategoryBrand categoryBrand);

    void deleteCategoryBrandById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
