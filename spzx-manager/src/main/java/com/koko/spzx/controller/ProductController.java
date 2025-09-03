package com.koko.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.product.ProductDto;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/product/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /* 列表查询。按照品牌id和分类id查询产品，带分页 */
    @GetMapping("/{pageNum}/{pageSize}")
    public Result findProductByPage(@PathVariable Integer pageNum,
                                    @PathVariable Integer pageSize,
                                    ProductDto productDto) {
        PageInfo pageInfo = service.findProductByPage(pageNum, pageSize, productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

}
