package com.koko.spzx.service;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.entity.product.ProductSpec;

import java.util.List;

public interface ProductSpecService {

    PageInfo<ProductSpec> findByPage(Integer pageNum, Integer pageSize);

    void insertProductSpec(ProductSpec productSpec);

    void updateProductSpecById(ProductSpec productSpec);

    void deleteProductSpecById(Long id);

    List<ProductSpec> findAllProductSpecs();

}
