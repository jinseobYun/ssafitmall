package com.ssafy.ssafitmall.model.service;

import java.util.List;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductSearchCondition;
import com.ssafy.ssafitmall.model.dto.User;

public interface AdminService {
	// 모든 유저 정보 조회
	public List<User> findAll(String role);

	// 유저 한명 정보 조회
	public User findUserById(String userId);

	// 유저 등록
	public int writeUser(User user);

	// 유저 정보 수정
	public int modifyUser(User user);

	// 유저 삭제
	public boolean removeUser(String userId);

	// 상품 검수 상태 수정
	public int modifyProductInspection(int productCode, String status);

	// 상품 수정
	public int modifyProduct (Product product);

	Product findOneProduct(int productCode);

	List<Product> findAllProduct(ProductSearchCondition condition);
}