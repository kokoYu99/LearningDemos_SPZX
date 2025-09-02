package com.koko.spzx.service;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.entity.product.Brand;

import java.util.List;

public interface BrandService {
    PageInfo<Brand> getBrandListByPage(Integer pageNum, Integer pageSize);

    void insertBrand(Brand brand);

    void updateBrandById(Brand brand);

    void deleteBrandById(Long id);

    List<Brand> findAllBrands();
}
