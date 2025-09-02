package com.koko.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.spzx.exception.BusinessException;
import com.koko.spzx.mapper.BrandMapper;
import com.koko.spzx.model.entity.product.Brand;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandMapper mapper;

    public BrandServiceImpl(BrandMapper mapper) {
        this.mapper = mapper;
    }

    /* 查询所有品牌数据，并分页 */
    @Override
    public PageInfo<Brand> getBrandListByPage(Integer pageNum, Integer pageSize) {
        //测试是否接到正确的分页值
        System.out.println("pageNum = " + pageNum + ", pageSize = " + pageSize);

        //设置分页参数
        PageHelper.startPage(pageNum, pageSize);

        //获取所有品牌数据集合
        List<Brand> brandList = mapper.getAllBrandList();

        //分页
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);

        //返回分页后的品牌数据
        return pageInfo;

    }

    /* 新增品牌 */
    @Override
    public void insertBrand(Brand brand) {
        int i = mapper.insertBrand(brand);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /* 修改品牌 */
    @Override
    public void updateBrandById(Brand brand) {
        int i = mapper.updateBrandById(brand);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /* 删除品牌 */
    @Override
    public void deleteBrandById(Long id) {
        int i = mapper.deleteBrandById(id);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /* 查询所有品牌 */
    @Override
    public List<Brand> findAllBrands() {
        List<Brand> brandList = mapper.getAllBrandList();
        return brandList;
    }
}
