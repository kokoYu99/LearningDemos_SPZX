package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {

    List<ProductSpec> findAllProductSpecs();

    int insertProductSpec(ProductSpec productSpec);

    int updateProductSpecById(ProductSpec productSpec);

    int deleteProductSpecById(Long id);
}
