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

    void updateProductInfo(Product product);

    Product findProductById(Long id);

    void saveProductInfo(Product product);

    PageInfo findProductByPage(Integer pageNum, Integer pageSize, ProductDto productDto);


    void deleteProductById(Long productId);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateAvailableStatus(Long id, Integer status);
}
