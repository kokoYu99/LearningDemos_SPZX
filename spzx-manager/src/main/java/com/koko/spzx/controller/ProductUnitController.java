package com.koko.spzx.controller;

import com.koko.spzx.mapper.ProductMapper;
import com.koko.spzx.model.entity.base.ProductUnit;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.ProductUnitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {
    private final ProductUnitService service;

    public ProductUnitController(ProductUnitService service) {
        this.service = service;
    }

    /* 获取所有商品计量单位 */
    @GetMapping("/findAll")
    public Result findAllProductUnits() {
        List<ProductUnit> list = service.findAllProductUnits();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }


}
