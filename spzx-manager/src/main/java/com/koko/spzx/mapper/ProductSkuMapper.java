package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {

    int insertSku(ProductSku productSku);

    List<ProductSku> findSkusByProductId(Long productId);

    int updateSku(ProductSku sku);

}
