package com.ssafy.ssafitmall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ssafy.ssafitmall.model.dto.*;
import com.ssafy.ssafitmall.model.service.OrderService;
import com.ssafy.ssafitmall.model.service.ProductService;
import com.ssafy.ssafitmall.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.ssafitmall.model.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "AdminController", description = "관리자 페이지")

@CrossOrigin(value = "*")
public class AdminController {

    private final AdminService adminService;

    private final OrderService orderService;

    private final ProductService productService;

    private static final String FAIL_GET_ALL_USERS = "모든 유저 조회 실패";
    private static final String FAIL_GET_USER = "유저 한명 조회 실패";
    private static final String FAIL_POST_USER = "유저 등록 실패";
    private static final String FAIL_PUT_USER = "유저 정보 수정 실패";
    private static final String FAIL_DELETE_USER = "유저 삭제 실패";
    private static final String FAIL_GET_ALL_PRODUCT = "전체 상품 조회 실패";
    private static final String FAIL_PUT_PRODUCT = "상품 수정 실패";

    @Autowired
    public AdminController(AdminService adminService
            , OrderService orderService, ProductService productService) {
        this.adminService = adminService;
        this.orderService = orderService;
        this.productService = productService;
    }

    // 모든 유저 조회
    @GetMapping("/user")
    @Operation(summary = "모든 유저 조회", description = "관리자가 모든 유저를 조회하는 기능")
    public ResponseEntity<?> list(@RequestParam("role") String role) {
        System.out.println(role);
        // 서비스에서 모든 유저 목록을 반환받아 조회
        List<User> list = adminService.findAll(role);
        if (list == null || list.size() == 0)
            return new ResponseEntity<String>(FAIL_GET_ALL_USERS, HttpStatus.NO_CONTENT);
        // body에 결과를, 상태코드에 OK를 담아서 리턴 해줌
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    // 유저 한명 조회
    @GetMapping("/user/{userId}")
    @Operation(summary = "유저 한명 조회", description = "관리자가 ID를 입력받아 유저 한명을 조회하는 기능")
    public ResponseEntity<?> detail(@PathVariable("userId") String userId) {
        HashMap<String, Object> map = new HashMap<>();
        User user = adminService.findUserById(userId);
        if (user == null) {
            return new ResponseEntity<String>(FAIL_GET_USER, HttpStatus.NO_CONTENT);
        }
        map.put("user", user);

        List<Order> orderInfoList = orderService.findOrdersByCustomerId(userId);
        if (orderInfoList == null || orderInfoList.isEmpty()) {

            map.put("orderInfoList", null);
            map.put("productList", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            List<Product> productList = new ArrayList<>();
            for (Order order : orderInfoList) {
                    Product product = productService.findOneProduct(order.getProductCode());
                    productList.add(product);
            }
            map.put("orderInfoList", orderInfoList);
            map.put("productList", productList);
            return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
        }
    }

    // 유저 등록
    @PostMapping("/user")
    @Operation(summary = "유저 등록", description = "관리자가 유저를 DB에 등록하는 기능")
    public ResponseEntity<?> write(@RequestBody User user) {
        // 성공시 1, 실패시 0을 반환하는 service의 메소드 실행
        int result = adminService.writeUser(user);
        if (result == 0)
            return new ResponseEntity<String>(FAIL_POST_USER, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Integer>(1, HttpStatus.OK);
    }

    // 유저 정보 수정
    @PutMapping("/user/{userId}")
    @Operation(summary = "유저 정보 수정", description = "관리자가 유저를 수정하는 기능")
    public ResponseEntity<?> update(@PathVariable("userId") String userId, @RequestBody User user) {
        user.setUserId(userId);

        // 필수 필드 검증
        if (user.getUserPwd() == null || user.getUserPwd().isEmpty()) {
            return new ResponseEntity<>(FAIL_PUT_USER, HttpStatus.BAD_REQUEST);
        }

        int result = adminService.modifyUser(user);
        if (result == 0)
            return new ResponseEntity<>(FAIL_PUT_USER, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    // 유저 삭제
    @DeleteMapping("/user/{userId}")
    @Operation(summary = "유저 삭제", description = "관리자가 유저를 삭제하는 기능")
    public ResponseEntity<?> delete(@PathVariable("userId") String userId) {
        boolean result = adminService.removeUser(userId);
        if (!result)
            return new ResponseEntity<>(FAIL_DELETE_USER, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // 상품 리스트
    @GetMapping("/product")
    public ResponseEntity<?> listProduct(@ModelAttribute ProductSearchCondition condition) {
        List<Product> list = adminService.findAllProduct(condition);
        if (list == null ){
            return new ResponseEntity<>(FAIL_GET_ALL_PRODUCT, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/product/inspection")
    public ResponseEntity<?> updateInspection(@RequestBody ProductInspection status){

        int result = adminService.modifyProductInspection(status.getProductCode(), status.getInspection());
        if (result == 0){
            return new ResponseEntity<>(FAIL_PUT_PRODUCT,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/product/user/{userId}")
    public ResponseEntity< ?> getUserProduct(@PathVariable("userId") String userId) {
        List<Product> list = productService.findUserProductList(userId);
        if (list == null) {
            return new ResponseEntity<>(FAIL_GET_ALL_PRODUCT,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    @GetMapping("/product/{productCode}")
    public ResponseEntity<?> getProductByProductCode(@PathVariable("productCode") int productCode) {
        Product product = adminService.findOneProduct(productCode);
        if(product ==null){
            return new ResponseEntity<>(FAIL_GET_ALL_PRODUCT,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/product/{productCode}")
    public ResponseEntity<?> updateProduct(@PathVariable("productCode") int productCode, @RequestBody Product product) {
        int result = adminService.modifyProduct(product);
        if (result == 0){
            return new ResponseEntity<>(FAIL_PUT_PRODUCT,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }



}
