package com.koko.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.spzx.exception.BusinessException;
import com.koko.spzx.mapper.CategoryBrandMapper;
import com.koko.spzx.model.dto.product.CategoryBrandDto;
import com.koko.spzx.model.entity.product.Brand;
import com.koko.spzx.model.entity.product.CategoryBrand;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.CategoryBrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    private final CategoryBrandMapper mapper;

    public CategoryBrandServiceImpl(CategoryBrandMapper mapper) {
        this.mapper = mapper;
    }

    /* 查询分类品牌数据，并分页。根据分类和品牌id */
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto) {

        PageHelper.startPage(pageNum, pageSize);

        List<CategoryBrand> list = mapper.findByPage(categoryBrandDto);

        PageInfo<CategoryBrand> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    /* 新增分类品牌 */
    @Override
    public void insertCategoryBrand(CategoryBrand categoryBrand) {
        int i = mapper.insertCategoryBrand(categoryBrand);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /* 修改分类品牌 */
    @Override
    public void updateCategoryBrandById(CategoryBrand categoryBrand) {
        int i = mapper.updateCategoryBrandById(categoryBrand);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /* 删除分类品牌 */
    @Override
    public void deleteCategoryBrandById(Long id) {
        int i = mapper.deleteCategoryBrandById(id);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
