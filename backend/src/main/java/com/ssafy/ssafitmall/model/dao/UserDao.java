package com.ssafy.ssafitmall.model.dao;

import com.ssafy.ssafitmall.model.dto.Seller;
import com.ssafy.ssafitmall.model.dto.User;

public interface UserDao {

	// 유저 로그인
	public User selectUserById(String userId);

	// 유저 회원가입
	public int createUser(User user);
	
	// 본인 정보 수정
	public int updateUser(User user);
	
	// 유저 탈퇴
	public int deleteUser(String userId);
	
	//아이디찾기
	public String selectIdByEmail(String userEmail);

	// 판매자 정보 이볅
	public int createSeller(Seller seller);

}
