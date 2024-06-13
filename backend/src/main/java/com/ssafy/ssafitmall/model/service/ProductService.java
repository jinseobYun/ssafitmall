package com.ssafy.ssafitmall.model.service;

import java.util.List;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductImg;
import com.ssafy.ssafitmall.model.dto.ProductSearchCondition;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {


    // 전체 상품 조회
    public List<Product> findAllProducts(ProductSearchCondition condition);

    // 선택 상품 조회
    public Product findOneProduct(int productCode);

    // 상품 등록
    public int writeProduct(Product product);

    // 상품 수정
    public int modifyProduct(Product product);

    // 상품 삭제
    public int removeProduct(int productCode);

    // 상품 조회수 변경
    public int modifyProductHist(int productCode);


    // 상품 이미지 등록
    public int writeProductImgPath(ProductImg productImg);

    // 상품 썸네일 수정
    int modifyThumbnail(int productCode, String productThumbnail);
    // 상품 이미지 검색
    List<ProductImg> findProductImgs(int productCode);

    // 상품 이미지 삭제
    int removeProductImg(int productCode);

    // 유저 상품 검색
    List<Product> findUserProductList(String userId);

    int modifyProductSold(int productCode, String status);

    public List<Product> findtBestProductList(int limit);

    public List<Product> findtLatestProductList(int limit);
}
