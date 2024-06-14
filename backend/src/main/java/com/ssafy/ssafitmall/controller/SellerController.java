package com.ssafy.ssafitmall.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductImg;
import com.ssafy.ssafitmall.model.service.SellerService;
import com.ssafy.ssafitmall.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/seller")
@Tag(name = "SellerController", description = "판매자 페이지")
public class SellerController {
	private final SellerService sellerService;

	public SellerController(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	private static final String SUCCESS = "성공";

	private static final String FAIL_GET_ALL_PRODUCTS = "판매 상품 모두 조회 실패";
	private static final String FAIL_GET_PRODUCT = "판매 상품 하나 조회 실패";
	private static final String FAIL_POST_PRODUCT = "판매 상품 등록 실패";
	private static final String FAIL_PUT_PRODUCT = "판매 상품 수정 실패";
	private static final String FAIL_DELETE_PRODUCT = "판매 상품 삭제 실패";
	private static final String FAIL_POST_IMG = "판매 상품 이미지 등록 실패";
	private static final String FAIL_POST_thumbnail = "판매 상품 썸네일 등록 실패";
	private static final String FAIL_DELETE_IMG = "판매 상품 이미지 삭제 실패";

	// 파일 저장 위치
	private final String thumbnailRes = "C:\\final_project\\ssafitshoppingmall\\ssafit_mall_front\\src\\assets\\img\\product\\thumbnail";
	private final String imgsRes = "C:\\final_project\\ssafitshoppingmall\\ssafit_mall_front\\src\\assets\\img\\product\\content";

	// 판매 상품 모두 조회
	@GetMapping("/product")
	@Operation(summary = "판매 상품 모두 조회", description = "판매자가 판매 상품 모두 조회하는 기능")
	public ResponseEntity<?> list() {
		List<Product> list = sellerService.findSellerProducts();
		if (list == null || list.isEmpty()) {
			return new ResponseEntity<>(FAIL_GET_ALL_PRODUCTS, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// 판매 상품 하나 조회
	@GetMapping("/product/{productCode}")
	@Operation(summary = "판매 상품 하나 조회", description = "판매자가 판매 상품 하나 조회하는 기능")
	public ResponseEntity<?> detail(@PathVariable("productCode") int productCode) {

		Product product = sellerService.findSellerProductByCode(productCode);

		if (product == null)
			return new ResponseEntity<String>(FAIL_GET_PRODUCT, HttpStatus.NO_CONTENT);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	// 판매 상품 등록
	@PostMapping("/product")
	@Operation(summary = "판매 상품 등록", description = "판매자가 판매 상품 등록하는 기능")
	public ResponseEntity<?> write(@RequestBody Product product) {
		// 성공시 1, 실패시 0을 반환하는 service의 메소드 실행
		int result = sellerService.writeSellerProduct(product);
		if (result == 0)
			return new ResponseEntity<String>(FAIL_POST_PRODUCT, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Integer>(product.getProductCode(), HttpStatus.OK);
	}

	// 판매 상품 수정
	@PutMapping("/product/{productCode}")
	@Operation(summary = "판매 상품 수정", description = "판매자가 판매 상품 수정하는 기능")
	public ResponseEntity<?> update(@PathVariable("productCode") int productCode, @RequestBody Product product) {
		
		System.out.println(product);
		product.setProductCode(productCode);
		int result = sellerService.modifySellerProduct(product);
		System.out.println(result);
		if (result == 0)
			return new ResponseEntity<String>(FAIL_PUT_PRODUCT, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 판매 상품 삭제
	@DeleteMapping("/product/{productCode}")
	@Operation(summary = "판매 상품 삭제", description = "판매자가 판매 상품 삭제하는 기능")
	public ResponseEntity<?> delete(@PathVariable("productCode") int productCode) {
		int result = sellerService.removeSellerProduct(productCode);
		if (result == 0)
			return new ResponseEntity<String>(FAIL_DELETE_PRODUCT, HttpStatus.NOT_FOUND);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 판매 상품 이미지 등록
	@PostMapping("/img/{productCode}")
	@Operation(summary = "판매 상품 이미지 등록", description = "판매자가 판매 상품 이미지를 등록하는 기능")
	public ResponseEntity<?> uploadImg(@PathVariable("productCode") int productCode,
			@RequestParam("files") List<MultipartFile> files) throws IllegalStateException, IOException {

		System.out.println("code" + productCode);
		System.out.println(files);

		if (files != null && !files.isEmpty()) {
			for (MultipartFile file : files) {
				if (file != null) {
					String filePath = System.currentTimeMillis() + "_" + file.getOriginalFilename();
					ProductImg productImg = new ProductImg(file.getOriginalFilename(), filePath, productCode);
					file.transferTo(new File(imgsRes, filePath));
					int result = sellerService.writeSellerImgPath(productImg);
					if (result != 1) {
						return new ResponseEntity<>(FAIL_POST_PRODUCT, HttpStatus.BAD_REQUEST);
					}
				}
			}
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<>(FAIL_POST_IMG, HttpStatus.BAD_REQUEST);
	}

	// 판매 상품 썸네일 등록
	@PostMapping("/thumbnail/{productCode}")
	public ResponseEntity<String> uploadThumbnail(@PathVariable("productCode") int productCode,
			@RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {

		System.out.println("code: " + productCode);
		System.out.println("thumb");
		System.out.println(thumbnail);
		if (thumbnail != null && !thumbnail.isEmpty()) {
			String thumbnailPath = System.currentTimeMillis() + "_" + thumbnail.getOriginalFilename();
			thumbnail.transferTo(new File(thumbnailRes, thumbnailPath));
			int result = sellerService.modifySellerThumbnail(productCode, thumbnailPath);
			if (result == 1) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<>(FAIL_POST_thumbnail, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(FAIL_POST_thumbnail, HttpStatus.BAD_REQUEST);
	}

	// 판매 상품 이미지 삭제
	@DeleteMapping("img/{productCode}")
	public ResponseEntity<String> deleteImg(@PathVariable("productCode") int productCode) {
		int result = sellerService.removeSellerProductImg(productCode);


		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

}
