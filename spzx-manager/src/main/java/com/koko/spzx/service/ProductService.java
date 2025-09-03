package com.koko.spzx.service;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.product.ProductDto;

public interface ProductService {
    PageInfo findProductByPage(Integer pageNum, Integer pageSize, ProductDto productDto);
}
