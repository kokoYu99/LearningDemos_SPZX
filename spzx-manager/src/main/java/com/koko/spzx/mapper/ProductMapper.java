package com.koko.spzx.mapper;

import com.koko.spzx.model.dto.product.ProductDto;
import com.koko.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    int updateProduct(Product product);

    Product findProductById(Long productId);

    int insertProduct(Product product);

    List<Product> findProducts(ProductDto productDto);

    int deleteProductById(Long productId);

}
