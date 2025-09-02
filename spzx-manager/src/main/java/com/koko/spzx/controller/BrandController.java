package com.koko.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.entity.product.Brand;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {
    private final BrandService service;

    public BrandController(BrandService service) {
        this.service = service;
    }

    /* 查询所有品牌数据 */
    @GetMapping("/{pageNum}/{pageSize}")
    public Result getBrandListByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<Brand> brandListByPage = service.getBrandListByPage(pageNum, pageSize);
        return Result.build(brandListByPage, ResultCodeEnum.SUCCESS);
    }

    /* 新增品牌 */
    @PostMapping("/save")
    public Result insertBrand(@RequestBody Brand brand) {
        service.insertBrand(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 修改品牌 */
    @PutMapping("/updateById")
    public Result updateBrandById(@RequestBody Brand brand) {
        service.updateBrandById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 删除品牌 */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteBrandById(@PathVariable Long id) {
        service.deleteBrandById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 分类品牌管理，当页面加载完毕以后查询出系统中所有的品牌数据，将品牌数据在搜索表单的品牌下拉框中进行展示 */
    @GetMapping("/findAll")
    public Result findAll() {
        List<Brand> brandList = service.findAllBrands();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }


}
