package com.ssafy.ssafitmall.model.dao;

import java.util.HashMap;
import java.util.List;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductImg;

public interface SellerDao {

	// 판매 상품 모두 조회
	public List<Product> selectSellerProducts();

	// 판매 상품 하나 조회
	public Product selectSellerProductByCode(int productCode);

	// 판매 상품 등록
	public int createSellerProduct(Product product);

	// 판매 상품 수정
	public int updateSellerProduct(Product product);

	// 판매 상품 삭제
	public int deleteSellerProduct(int productCode);
	
	// 판매 상품 이미지 생성
	public int createSellerImgPath(ProductImg productImg);
	
	// 판매 상품 썸네일 수정
	public int updateSellerThumbnail(HashMap<String, Object> param);
	
	// 판매 상품 이미지 경로 삭제
	public int deleteSellerProductImg(int productCode);

}
