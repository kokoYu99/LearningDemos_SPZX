package com.koko.spzx.service;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.exception.BusinessException;
import com.koko.spzx.model.dto.product.ProductDto;
import com.koko.spzx.model.entity.product.Product;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

public interface ProductService {

    /* 修改商品数据 */
    @Transactional(
            timeout = 5, //超时时间10s
            rollbackFor = {SQLException.class, BusinessException.class}, //遇到这些异常，就回滚。运行时异常默认会回滚
            isolation = READ_COMMITTED, //隔离级别：读已提交
            propagation = Propagation.REQUIRED) //传播行为：调用者有事务就加入，无事务就单独开事务(默认)
    void updateProductInfo(Product product);

    Product findProductById(Long id);

    void saveProductInfo(Product product);

    PageInfo findProductByPage(Integer pageNum, Integer pageSize, ProductDto productDto);


}
