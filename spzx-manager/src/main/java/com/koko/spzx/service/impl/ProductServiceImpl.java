package com.koko.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.spzx.mapper.ProductMapper;
import com.koko.spzx.model.dto.product.ProductDto;
import com.koko.spzx.model.entity.product.Product;
import com.koko.spzx.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PageInfo findProductByPage(Integer pageNum, Integer pageSize, ProductDto productDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> list = mapper.findProducts(productDto);
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
