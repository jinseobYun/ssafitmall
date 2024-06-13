package com.ssafy.ssafitmall.model.dao;

import java.util.HashMap;
import java.util.List;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductImg;
import com.ssafy.ssafitmall.model.dto.ProductSearchCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public interface ProductDao {
	
	// 전체 상품 조회
	public List<Product> selectAllProducts(ProductSearchCondition condition);
	
	// 선택 상품 조회
	public Product selectOneProduct(int productCode);
	
	// 상품 등록 
	public int createProduct(Product product);

	// 상품 이미지 생성
	public int createImgPath(ProductImg productImg);
	
	// 상품 수정
	public int updateProduct(Product product);
	
	// 상품 삭제
	public int deleteProduct(int productCode);
	
	// 상품 조회수 변경 
	public int 	updateProductHist(int productCode);

	// 썸네일 수정
	public int updateThumbnail(HashMap<String, Object> param);

	// 이미지 검색
	public List<ProductImg> selectProductImg(int productCode);

	// 이미지 경로 삭제
	public int deleteProductImg(int productCode);

	// 유저 상품 검색
	public List<Product> selectUserProducts(String userId);

	// 상품 판매 현황 수정
	public int updateSold(HashMap<String, Object> param);

	public List<Product> selsectBestProductList(int limit);

	public List<Product> selectLatestProductList(int limit);

}
