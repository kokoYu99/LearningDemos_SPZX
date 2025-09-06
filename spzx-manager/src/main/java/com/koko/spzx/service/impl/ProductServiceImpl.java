package com.koko.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.spzx.exception.BusinessException;
import com.koko.spzx.mapper.ProductDetailsMapper;
import com.koko.spzx.mapper.ProductMapper;
import com.koko.spzx.mapper.ProductSkuMapper;
import com.koko.spzx.model.dto.product.ProductDto;
import com.koko.spzx.model.entity.product.Product;
import com.koko.spzx.model.entity.product.ProductDetails;
import com.koko.spzx.model.entity.product.ProductSku;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;


//注：方法名中的productInfo，包括商品基本信息(product表)、详细信息(product_details)、sku(product_sku)
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductDetailsMapper detailsMapper;
    private final ProductSkuMapper skuMapper;

    public ProductServiceImpl(ProductMapper productMapper, ProductDetailsMapper detailsMapper, ProductSkuMapper skuMapper) {
        this.productMapper = productMapper;
        this.detailsMapper = detailsMapper;
        this.skuMapper = skuMapper;
    }


    /* 修改商品审核状态和审核信息 */
    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {

        /* 方式1. 先从数据库获取product对象，设置审核状态，再传回数据库，进行修改
         * 注：这样比方式2更安全，降低了数据覆盖风险
         */
        Product product = productMapper.findProductById(id);
        if (product == null) {
            throw new BusinessException(ResultCodeEnum.PRODUCT_NOT_FOUND);
        }
        //如果审核状态没有改变，就无需修改
        if (product.getAuditStatus().equals(auditStatus)) {
            return;
        }
        //审核状态改变了，进行修改
        product.setAuditStatus(auditStatus);
        product.setAuditMessage(auditStatus == 1 ? "审批通过" : "审批不通过");


        /* 方式2. 新建product对象，再传给数据库，进行修改 */
//        //创建product对象
//        Product product = new Product();
//        //用前端传入的数据赋值
//        product.setAuditStatus(auditStatus);
//        product.setAuditMessage(auditStatus == 1 ? "审批通过" : "审批不通过");


        //将修改后的对象传给数据库
        int i = productMapper.updateProduct(product);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

    }


    /* 修改商品上下架状态 */
    @Override
    public void updateAvailableStatus(Long id, Integer status) {

        /* 先从数据库获取product对象，设置审核状态，再传回数据库，进行修改 */
        Product product = productMapper.findProductById(id);
        if (product == null) {
            throw new BusinessException(ResultCodeEnum.PRODUCT_NOT_FOUND);
        }
        //如果上下架状态没有改变，就无需修改
        if (product.getStatus().equals(status)) {
            return;
        }
        product.setStatus(status);

        //将修改后的对象传给数据库
        int i = productMapper.updateProduct(product);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }


    /* 删除商品数据，三张表对应的数据 */
    @Transactional(
            timeout = 5, //超时时间
            rollbackFor = {SQLException.class, BusinessException.class}, //遇到这些异常，就回滚。运行时异常默认会回滚
            isolation = READ_COMMITTED, //隔离级别：读已提交
            propagation = Propagation.REQUIRED) //传播行为：调用者有事务就加入，无事务就单独开事务(默认)
    @Override
    public void deleteProductById(Long productId) {
        //删除product表的数据，根据id
        int i = productMapper.deleteProductById(productId);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        //删除sku表的数据，根据product_id
        int j = skuMapper.deleteSkuByProductId(productId);
        if (j < 1) { //一个商品至少有一个sku
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        //删除details表的数据，根据product_id
        int k = detailsMapper.deleteDetailsByProductId(productId);
        if (k != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }


    /* 修改商品数据 */
    @Transactional(
            timeout = 5, //超时时间
            rollbackFor = {SQLException.class, BusinessException.class}, //遇到这些异常，就回滚。运行时异常默认会回滚
            isolation = READ_COMMITTED, //隔离级别：读已提交
            propagation = Propagation.REQUIRED) //传播行为：调用者有事务就加入，无事务就单独开事务(默认)
    @Override
    public void updateProductInfo(Product product) {
        /* 修改商品基本信息 */
        int i = productMapper.updateProduct(product);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        /* 修改商品sku信息 */
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (ProductSku sku : productSkuList) {
            int j = skuMapper.updateSku(sku);

            System.out.println("j = " + j);
            System.out.println(sku.getId());

            if (j != 1) {
                throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
            }
        }

        /* 修改商品详情信息 */
        //product中没有完整的details对象，先根据product_id从数据库获取
        ProductDetails productDetails = detailsMapper.findDetailsByProductId(product.getId());
        //修改其imageUrls的值
        productDetails.setImageUrls(product.getDetailsImageUrls());
        //保存修改后的details对象
        int k = detailsMapper.updateDetails(productDetails);

        System.out.println("k = " + k);
        System.out.println(productDetails.getId());

        if (k != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /*  查询商品，根据id。用于修改商品数据前回显数据 */
    @Override
    public Product findProductById(Long productId) {
        /* 获取商品基本信息 */
        Product product = productMapper.findProductById(productId);

        /* 获取商品sku信息 */
        List<ProductSku> productSkuList = skuMapper.findSkusByProductId(productId);
        //存入product对象
        product.setProductSkuList(productSkuList);

        /* 获取商品详情信息 */
        ProductDetails productDetails = detailsMapper.findDetailsByProductId(productId);
        product.setDetailsImageUrls(productDetails.getImageUrls());

        return product;
    }

    /* 保存商品信息  */
    @Transactional(
            timeout = 5, //超时时间
            rollbackFor = {SQLException.class, BusinessException.class}, //遇到这些异常，就回滚。运行时异常默认会回滚
            isolation = READ_COMMITTED, //隔离级别：读已提交
            propagation = Propagation.REQUIRED) //传播行为：调用者有事务就加入，无事务就单独开事务(默认)
    @Override
    public void saveProductInfo(Product product) {
        /* 保存商品基本信息 */
        product.setStatus(0);
        product.setAuditStatus(0);
        //保存product时，useGeneratedKeys，生成主键回显
        int i = productMapper.insertProduct(product);
        if (i != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }

        /* 保存sku数据 */
        //获取sku集合
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int k = 0; k < productSkuList.size(); k++) {
            //获取sku对象
            ProductSku productSku = productSkuList.get(k);

            //设置商品id
            productSku.setProductId(product.getId()); //使用生成的id主键值
            //设置skuCode，要求唯一
            //格式1. 商品id_sku，如 1_0, 1_1, ...
            //格式2. UUID，进行截取(不超过30位)
            productSku.setSkuCode(productSku.getProductId() + "_" + k);
            //设置skuName 商品名_规格名
            productSku.setSkuName(product.getName() + "_" + productSku.getSkuSpec());
            //设置销量
            productSku.setSaleNum(0);
            //设置状态
            productSku.setStatus(0); //初始值=0，需要先审批再上架(=1)

            //保存sku数据
            int j = skuMapper.insertSku(productSku);
            if (j != 1) {
                throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
            }
        }

        /* 保存商品详情数据 */
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        int h = detailsMapper.insertDetails(productDetails);
        if (h != 1) {
            throw new BusinessException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }


    /* 列表查询。按照品牌id和分类id查询产品，带分页 */
    @Override
    public PageInfo findProductByPage(Integer pageNum, Integer pageSize, ProductDto productDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.findProducts(productDto);
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


}
