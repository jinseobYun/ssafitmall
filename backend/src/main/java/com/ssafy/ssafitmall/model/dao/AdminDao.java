package com.ssafy.ssafitmall.model.dao;

import java.util.HashMap;
import java.util.List;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductSearchCondition;
import org.springframework.stereotype.Repository;

import com.ssafy.ssafitmall.model.dto.User;


public interface AdminDao {

	//모든 유저 정보 조회
	public List<User> selectAll(String role);

	//유저 한명 정보 조회
	public User selectUserById(String userId);

	//유저 등록
	public int createUser(User user);

	//유저 정보 수정
	public int updateUser(User user);

	//유저 삭제
	public int deleteUser(String userId);

	// 상품 inspection 수정
	public int updateProductInspectionStatus(HashMap<String,Object> map);

	// 상품 수정
	public int updateProduct(Product product);


	// 전체 상품 조회
	public List<Product> selectProductList(ProductSearchCondition condition);

	// 상품 한개 조회
	Product selectProductByProductCode(int productCode);
}