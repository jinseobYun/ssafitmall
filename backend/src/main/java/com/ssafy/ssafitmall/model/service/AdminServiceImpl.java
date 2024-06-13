package com.ssafy.ssafitmall.model.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductSearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafitmall.model.dao.AdminDao;
import com.ssafy.ssafitmall.model.dto.User;

@Service
public class AdminServiceImpl implements AdminService{
	private final AdminDao adminDao;

	@Autowired
	public AdminServiceImpl(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	//모든 유저 조회
	@Override
	public List<User> findAll(String role) {
		return adminDao.selectAll(role);
	}

	//유저 상세 조회
	@Override
	public User findUserById(String userId) {
		return adminDao.selectUserById(userId);
	}

	//유저 등록
	@Override
	public int writeUser(User user) {
		return adminDao.createUser(user);
	}

	//유저 정보 수정
	@Override
	public int modifyUser(User user) {
		return adminDao.updateUser(user);
	}

	//유저 삭제
	@Override
	public boolean removeUser(String userId) {
		return adminDao.deleteUser(userId) > 0;
	}

	@Override
	public int modifyProductInspection(int productCode, String status) {
		HashMap<String,Object> map = new HashMap<>();
		map.put("productCode", productCode);
		map.put("status", status);
		return adminDao.updateProductInspectionStatus(map);
	}

	@Override
	public int modifyProduct(Product product) {
		return adminDao.updateProduct(product);
	}

	@Override
	public Product findOneProduct(int productCode) {
		return adminDao.selectProductByProductCode(productCode);
	}

	@Override
	public List<Product> findAllProduct(ProductSearchCondition condition) {
		return adminDao.selectProductList(condition);
	}

}
