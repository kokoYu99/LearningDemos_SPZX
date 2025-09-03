package com.koko.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.spzx.exception.BusinessException;
import com.koko.spzx.mapper.ProductSpecMapper;
import com.koko.spzx.model.entity.product.ProductSpec;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.ProductSpecService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {
    private final ProductSpecMapper mapper;

    public ProductSpecServiceImpl(ProductSpecMapper mapper) {
        this.mapper = mapper;
    }


    /* 查询所有规格 */
    @Override
    public List<ProductSpec> findAllProductSpecs() {
        List<ProductSpec> list = mapper.findAllProductSpecs();
        return list;
    }

    @Override
    public PageInfo<ProductSpec> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductSpec> list = mapper.findAllProductSpecs();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void insertProductSpec(ProductSpec productSpec) {
        int i = mapper.insertProductSpec(productSpec);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void updateProductSpecById(ProductSpec productSpec) {
        int i = mapper.updateProductSpecById(productSpec);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteProductSpecById(Long id) {
        int i = mapper.deleteProductSpecById(id);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }


}