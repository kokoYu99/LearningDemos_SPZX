package com.koko.spzx.service.impl;

import com.koko.spzx.mapper.ProductUnitMapper;
import com.koko.spzx.model.entity.base.ProductUnit;
import com.koko.spzx.service.ProductUnitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    private final ProductUnitMapper mapper;

    public ProductUnitServiceImpl(ProductUnitMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ProductUnit> findAllProductUnits() {
        List<ProductUnit> list=mapper.findAllProductUnits();
        return list;
    }
}
