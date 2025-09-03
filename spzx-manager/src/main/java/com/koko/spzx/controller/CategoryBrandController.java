package com.koko.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.product.CategoryBrandDto;
import com.koko.spzx.model.entity.product.Brand;
import com.koko.spzx.model.entity.product.CategoryBrand;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.CategoryBrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {
    private final CategoryBrandService service;

    public CategoryBrandController(CategoryBrandService service) {
        this.service = service;
    }

    /* 查询分类品牌数据，并分页。根据分类和品牌id */
    @GetMapping("/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, CategoryBrandDto categoryBrandDto) {
        PageInfo<CategoryBrand> pageInfo = service.findByPage(pageNum, pageSize, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /* 新增分类品牌 */
    @PostMapping("/save")
    public Result insertCategoryBrand(@RequestBody CategoryBrand categoryBrand) {
        service.insertCategoryBrand(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 修改分类品牌 */
    @PutMapping("/updateById")
    public Result updateCategoryBrandById(@RequestBody CategoryBrand categoryBrand) {
        service.updateCategoryBrandById(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 删除分类品牌 */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteCategoryBrandById(@PathVariable Long id) {
        service.deleteCategoryBrandById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /*
    商品管理页，
    根据分类id，查询品牌
    */
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId) {
        List<Brand> brandList = service.findBrandByCategoryId(categoryId);
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
