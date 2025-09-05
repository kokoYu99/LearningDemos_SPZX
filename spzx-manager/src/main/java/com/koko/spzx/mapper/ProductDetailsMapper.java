package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {

    int updateDetails(ProductDetails productDetails);

    ProductDetails findDetailsByProductId(Long productId);

    int insertDetails(ProductDetails productDetails);

}
