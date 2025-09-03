package com.koko.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.entity.product.ProductSpec;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.ProductSpecService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {

    private ProductSpecService service;

    public ProductSpecController(ProductSpecService service) {
        this.service = service;
    }


    /* 查询所有规格 */
    @GetMapping("/findAll")
    public Result findAllProductSpecs() {
        List<ProductSpec> list = service.findAllProductSpecs();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    /* 列表查询，带分页 */
    @GetMapping("/{pageNum}/{pageSize}")
    public Result findProductSpecByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<ProductSpec> pageInfo = service.findByPage(pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result insertProductSpec(@RequestBody ProductSpec productSpec) {
        service.insertProductSpec(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateProductSpecById(@RequestBody ProductSpec productSpec) {
        service.updateProductSpecById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteProductSpecById(@PathVariable Long id) {
        service.deleteProductSpecById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
