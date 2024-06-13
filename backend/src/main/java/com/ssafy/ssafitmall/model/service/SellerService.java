package com.ssafy.ssafitmall.model.service;

import java.util.List;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductImg;

public interface SellerService {
	// 판매 상품 모두 조회
	public List<Product> findSellerProducts();

	// 판매 상품 하나 조회
	public Product findSellerProductByCode(int productCode);

	// 판매 상품 등록
	public int writeSellerProduct(Product product);

	// 판매 상품 수정
	public int modifySellerProduct(Product product);

	// 판매 상품 삭제
	public int removeSellerProduct(int productCode);
	
	// 판매 상품 이미지 등록
    public int writeSellerImgPath(ProductImg productImg);

    // 판매 상품 썸네일 수정
    int modifySellerThumbnail(int productCode, String productThumbnail);
    
    // 판매 상품 이미지 삭제
    int removeSellerProductImg(int productCode);

}
