package com.koko.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.product.ProductDto;
import com.koko.spzx.model.entity.product.Product;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /* 保存商品信息，
    包括商品基本信息(product表)、详细信息(product_details)、sku(product_sku) */
    @PostMapping("/save")
    public Result saveProductInfo(@RequestBody Product product) {
        service.saveProductInfo(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 列表查询。按照品牌id和分类id查询产品，带分页 */
    @GetMapping("/{pageNum}/{pageSize}")
    public Result findProductByPage(@PathVariable Integer pageNum,
                                    @PathVariable Integer pageSize,
                                    ProductDto productDto) {
        PageInfo pageInfo = service.findProductByPage(pageNum, pageSize, productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /* 查询商品信息，根据id */
    @GetMapping("/getById/{id}")
    public Result findProductById(@PathVariable Long id) {
        Product product = service.findProductById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    /* 修改商品信息 */
    @PutMapping("/updateById")
    public Result updateProductById(@RequestBody Product product) {
        service.updateProductInfo(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 删除商品信息 */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteProductById(@PathVariable Long id) {
        service.deleteProductById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 修改商品审核状态和审核信息 */
    @PutMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id, @PathVariable Integer auditStatus) {
        service.updateAuditStatus(id, auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 修改商品上下架状态 */
    @PutMapping("/updateStatus/{id}/{status}")
    public Result updateAvailableStatus(@PathVariable Long id, @PathVariable Integer status) {
        service.updateAvailableStatus(id, status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
