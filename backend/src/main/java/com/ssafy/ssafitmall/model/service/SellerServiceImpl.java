

package com.ssafy.ssafitmall.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafitmall.model.dao.SellerDao;
import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductImg;

@Service
public class SellerServiceImpl implements SellerService {

	private final SellerDao sellerDao;

	@Autowired
	public SellerServiceImpl(SellerDao sellerDao) {
		this.sellerDao = sellerDao;
	}

	//판매 상품 모두 조회
	@Override
	public List<Product> findSellerProducts() {
		return sellerDao.selectSellerProducts();
	}

	// 판매 상품 하나 조회
	@Override
	public Product findSellerProductByCode(int productCode) {
		return sellerDao.selectSellerProductByCode(productCode);
	}

	// 판매 상품 등록
	@Override
	public int writeSellerProduct(Product product) {
		return sellerDao.createSellerProduct(product);
	}

	// 판매 상품 수정
	@Override
	public int modifySellerProduct(Product product) {
		return sellerDao.updateSellerProduct(product);
	}

	// 판매 상품 삭제
	@Override
	public int removeSellerProduct(int productCode) {
		return sellerDao.deleteSellerProduct(productCode);
	}

	// 판매 상품 이미지 등록
	@Override
	public int writeSellerImgPath(ProductImg productImg) {
		return sellerDao.createSellerImgPath(productImg);
	}

	// 판매 상품 썸네일 수정
	@Override
	public int modifySellerThumbnail(int productCode, String productThumbnail) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("productCode", productCode);
		map.put("productThumbnail",productThumbnail);
		return sellerDao.updateSellerThumbnail(map);
	}

	// 판매 상품 이미지 삭제
	@Override
	public int removeSellerProductImg(int productCode) {
		return sellerDao.deleteSellerProductImg(productCode);
	}

}