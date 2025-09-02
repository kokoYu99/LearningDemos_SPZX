package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> getAllBrandList();

    int insertBrand(Brand brand);

    int updateBrandById(Brand brand);

    int deleteBrandById(Long id);
}
