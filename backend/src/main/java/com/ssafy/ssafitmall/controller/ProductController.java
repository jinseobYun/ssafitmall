package com.ssafy.ssafitmall.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ssafy.ssafitmall.model.dto.ProductImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.ssafitmall.model.dto.Product;
import com.ssafy.ssafitmall.model.dto.ProductSearchCondition;
import com.ssafy.ssafitmall.model.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
@CrossOrigin(value = "*")
public class ProductController {

    private static final String SUCCESS = "success";
    private static final String FAIL_GET_LIST = "전체 상품 조회 실패";
    private static final String FAIL_GET_PRODUCT = "선택 상품 조회 실패";
    private static final String FAIL_POST_PRODUCT = "상품 등록 실패";
    private static final String FAIL_PUT_PRODUCT = "상품 수정 실패";
    private static final String FAIL_DELETE_PRODUCT = "상품 삭제 실패";

    private final ProductService productService;
    // 파일 저장 위치
    private final String thumbnailRes = "C:\\final_project\\ssafitshoppingmall\\ssafit_mall_front\\src\\assets\\img\\product\\thumbnail";
    private final String imgsRes = "C:\\final_project\\ssafitshoppingmall\\ssafit_mall_front\\src\\assets\\img\\product\\content";

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 전체 상품 조회
    @GetMapping("/")
    public ResponseEntity<?> list(@ModelAttribute ProductSearchCondition condition) {
        List<Product> list = productService.findAllProducts(condition); // 검색 조회
        if (list == null || list.size() == 0) {
            return new ResponseEntity<String>(FAIL_GET_LIST, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    // 선택 상품 조회
    @GetMapping("/{productCode}")
    public ResponseEntity<?> detail(@PathVariable("productCode") int productCode) {
        Product product = productService.findOneProduct(productCode);
        if (product != null)
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        return new ResponseEntity<String>(FAIL_GET_PRODUCT, HttpStatus.NOT_FOUND);


    }

    // 상품 등록
    @PostMapping("/")
    public ResponseEntity<?> write(@RequestBody Product product) {

        int result = productService.writeProduct(product);
        if (result == 1) {
            return new ResponseEntity<Integer>(product.getProductCode(), HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL_POST_PRODUCT, HttpStatus.BAD_REQUEST);
    }

    // 상품 수정
    @PutMapping("/{productCode}")
    public ResponseEntity<String> update(@PathVariable("productCode") int productCode, @RequestBody Product product) {


        product.setProductCode(productCode);
        int result = productService.modifyProduct(product);
        if (result == 1)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<String>(FAIL_PUT_PRODUCT, HttpStatus.BAD_REQUEST);
    }

    // 상품 삭제
    @DeleteMapping("/{productCode}")
    public ResponseEntity<String> delete(@PathVariable("productCode") int productCode) {
        int result = productService.removeProduct(productCode);
        if (result == 1)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<String>(FAIL_DELETE_PRODUCT, HttpStatus.NOT_FOUND);
    }


    // 상품 썸네일 등록
    @PostMapping("/thumbnail/{productCode}")
    public ResponseEntity<String> uploadThumbnail(@PathVariable("productCode") int productCode, @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {

        if (thumbnail != null) {
            String thumbnailPath = System.currentTimeMillis() + "_" + thumbnail.getOriginalFilename();
            thumbnail.transferTo(new File(thumbnailRes, thumbnailPath));
            int result = productService.modifyThumbnail(productCode, thumbnailPath);
            if (result == 1)
                return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            return new ResponseEntity<>(FAIL_POST_PRODUCT,
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("NOT_FOUND thumbnail",
                HttpStatus.OK);
    }

    // 상품 이미지 등록
    @PostMapping("/img/{productCode}")
    public ResponseEntity<String> uploadImg(@PathVariable("productCode") int productCode, @RequestParam("files") List<MultipartFile> files) throws IOException, Exception {

        if (files != null && files.size() > 0) {
            int result = 0;
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (file != null) {
                    String filePath = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    ProductImg productImg = new ProductImg(file.getOriginalFilename(), filePath, productCode);
                    // 파일 저장
                    file.transferTo(new File(imgsRes, filePath));
                    result = productService.writeProductImgPath(productImg);
                    if (result != 1)
                        return new ResponseEntity<>(FAIL_POST_PRODUCT, HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>("NOT_FOUND_File", HttpStatus.OK);
    }

    // 상품 이미지 조회
    @GetMapping("img/{productCode}")
    public ResponseEntity<?> getImg(@PathVariable("productCode") int productCode) {

        List<ProductImg> productImgs = productService.findProductImgs(productCode);

        if (productImgs != null && productImgs.size() > 0) {
            return new ResponseEntity<>(productImgs, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_GET_LIST, HttpStatus.NO_CONTENT);
    }

    // 상품 이미지 삭제
    @DeleteMapping("img/{productCode}")
    public ResponseEntity<String> deleteImg(@PathVariable("productCode") int productCode) {
        productService.removeProductImg(productCode);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }


}
