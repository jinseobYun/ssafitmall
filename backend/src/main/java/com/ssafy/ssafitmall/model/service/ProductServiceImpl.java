package com.ssafy.ssafitmall.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.ssafitmall.model.dto.ProductImg;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.ssafitmall.model.dao.ProductDao;
import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductSearchCondition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private Product product;

    ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAllProducts(ProductSearchCondition condition) {
        return productDao.selectAllProducts(condition);
    }

    @Override
    public Product findOneProduct(int productCode) {
        this.modifyProductHist(productCode);
        return productDao.selectOneProduct(productCode);
    }

    @Override
    public int writeProduct(Product product) {

        return productDao.createProduct(product);

    }

    @Override
    public int modifyProduct(Product product) {

        return productDao.updateProduct(product);
    }

    @Override
    public int removeProduct(int productCode) {
        return productDao.deleteProduct(productCode);
    }


    @Override
    public int modifyProductHist(int productCode) {

        return productDao.updateProductHist(productCode);
    }


    @Override
    public int writeProductImgPath(ProductImg productImg) {
        return productDao.createImgPath(productImg);
    }

    @Override
    public int modifyThumbnail(int productCode, String productThumbnail) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("productCode", productCode);
        map.put("productThumbnail", productThumbnail);
        return productDao.updateThumbnail(map);
    }

    @Override
    public List<ProductImg> findProductImgs(int productCode) {
        return productDao.selectProductImg(productCode);
    }

    @Override
    public int removeProductImg(int productCode) {
        return productDao.deleteProductImg(productCode);
    }

    @Override
    public List<Product> findUserProductList(String userId) {
        return productDao.selectUserProducts(userId)  ;
    }


    @Override
    public int modifyProductSold(int productCode, String status) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("productCode", productCode);
        map.put("status", status);
        return productDao.updateSold(map);
    }

    @Override
    public List<Product> findtBestProductList(int limit) {
        return productDao.selsectBestProductList(limit);
    }

    @Override
    public List<Product> findtLatestProductList(int limit) {
        return productDao.selectLatestProductList(limit);
    }


}
