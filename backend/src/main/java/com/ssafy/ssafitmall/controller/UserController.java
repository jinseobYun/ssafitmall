
package com.ssafy.ssafitmall.controller;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.ssafitmall.model.dao.UserDao;
import com.ssafy.ssafitmall.model.dto.Seller;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ssafy.ssafitmall.model.dto.User;
import com.ssafy.ssafitmall.model.service.UserService;
import com.ssafy.ssafitmall.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "유저 페이지")
public class UserController {

	private final UserService userService;

	private static final String SUCCESS = "성공";
	private static final String FAIL_POST_LOGIN = "유저 로그인 실패";
	private static final String FAIL_POST_SIGNUP = "회원가입 실패";
	private static final String FAIL_PUT_USER = "본인 정보 수정 실패";
	private static final String FAIL_DELETE_USER = "유저 탈퇴 실패";

	@Autowired
	private JwtUtil jwtUtil;
    @Autowired
    private UserDao userDao;

	@Autowired
	public UserController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	// 유저 로그인
	@PostMapping("/login")
	@Operation(summary = "유저 로그인", description = "유저가 로그인 하는 기능")
	public ResponseEntity<?> login(@RequestBody User user) {
		Map<String, Object> result = new HashMap<>();

		User loginUser = userService.loginUser(user.getUserId(), user.getUserPwd());

		if (loginUser == null) {
			return new ResponseEntity<String>(FAIL_POST_LOGIN, HttpStatus.UNAUTHORIZED);
		} else { // 로그인 성공
			result.put("message", SUCCESS);
			result.put("access-token", jwtUtil.createToken(loginUser));
			result.put("user", loginUser);

			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		}
	}

	// 로그아웃
	// 클라이언트에서 토큰을 삭제하면 됨
	@PostMapping("/logout")
	@Operation(summary = "로그아웃", description = "유저가 로그아웃 하는 기능")
	public ResponseEntity<?> logout() {
		Map<String, Object> result = new HashMap<>();
		result.put("message", SUCCESS);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 회원가입
	@PostMapping("/signup")
	@Operation(summary = "회원가입", description = "유저를 DB에 등록하는 기능")
	public ResponseEntity<?> signup(@RequestBody User user) {
		int result = userService.writeUser(user);
		if (result == 0)
			return new ResponseEntity<String>(FAIL_POST_SIGNUP, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(1, HttpStatus.OK);
	}

	// 본인 정보 수정
	@PutMapping("/{userId}")
	@Operation(summary = "유저 정보 수정", description = "유저를 본인 정보를 수정하는 기능")
	public ResponseEntity<?> update(@PathVariable("userId") String userId, @RequestBody User user) {
		user.setUserId(userId);
		System.out.println(user);

		// 필수 필드 검증
		if (user.getUserPwd() == null || user.getUserPwd().isEmpty()) {
			return new ResponseEntity<>(FAIL_PUT_USER, HttpStatus.BAD_REQUEST);
		}
		if(user.getUserRole() == null || user.getUserRole().length() == 0) {
			user.setUserRole("customer");
		}

		int result = userService.modifyUser(user);
		if (result == 0)
			return new ResponseEntity<>(FAIL_PUT_USER, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(1, HttpStatus.OK);
	}

	// 유저 탈퇴
	@DeleteMapping("/{userId}")
	@Operation(summary = "유저 탈퇴", description = "유저가 탈퇴하는 기능")
	public ResponseEntity<?> delete(@PathVariable("userId") String userId) {
		boolean result = userService.removeUser(userId);
		if (!result)
			return new ResponseEntity<>(FAIL_DELETE_USER, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	// 유저 정보 가져오기
	@GetMapping("/{userId}")
	@Operation(summary = "유저 정보 가져오기", description = "유저 정보를 가져오는 기능")
	public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
		User user = userService.getUserById(userId);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/findid/{userEmail}")
	@Operation(summary = "이메일 정보 받아서 아이디 찾기", description = "유저가 아이디를 찾는 기능")
	public ResponseEntity<?> getUserId(@PathVariable("userEmail") String userEmail) {
		String userId = userService.getIdByEmail(userEmail);
		if (userId == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(userId, HttpStatus.OK);
	}

	@GetMapping("/findpwd/{userEmail}/{userId}")
	@Operation(summary = "이메일, 아이디 정보 받아서 비밀번호 찾기", description = "유저가 비밀번호를 찾는 기능")
	public ResponseEntity<?> getUserPwd(@PathVariable("userEmail") String userEmail,
			@PathVariable("userId") String userId) {
		User result = userService.getUserById(userId);
		if (!result.getUserEmail().equals(userEmail) || result == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(result.getUserPwd(), HttpStatus.OK);
	}

	// 카카오 로그인
	@GetMapping("/login/kakao")
	@Operation(summary = "카카오 로그인 하기", description = "유저가 카카오로 간편로그인하는 기능")
	public ResponseEntity<?> kakologin(@RequestParam String code) {
	    try {
	        String accessToken = userService.getKakaoAccessToken(code);
	        User user = userService.getKakaoUserInfo(accessToken);
	        Map<String, Object> result = new HashMap<>();
	        result.put("message", SUCCESS);
	        result.put("access-token", jwtUtil.createToken(user));
	        result.put("user", user);
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("카카오 로그인 실패", HttpStatus.BAD_REQUEST);
	    }
	}


	@PostMapping("/seller")
	public ResponseEntity<?> seller(@RequestBody Seller seller) {
		System.out.println(seller);
		int result = userService.writeSeller(seller);
		if (result == 1){
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<>("판매자 정보 입력 실패",HttpStatus.BAD_REQUEST);
	}


}
