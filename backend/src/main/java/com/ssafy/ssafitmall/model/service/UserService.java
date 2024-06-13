
package com.ssafy.ssafitmall.model.service;

import com.ssafy.ssafitmall.model.dto.Seller;
import com.ssafy.ssafitmall.model.dto.User;

public interface UserService {

	// 유저 찾기
	public User findUserById(String userId);

	// 로그인
	public User loginUser(String userId, String userPwd);

	// 회원가입
	public int writeUser(User user);

	// 본인 정보 수정
	public int modifyUser(User user);

	// 유저 탈퇴
	public boolean removeUser(String userId);

	// 유저 정보 가져오기
	public User getUserById(String userId);
	
	// 이메일로 유저 아이디 찾기
	public String getIdByEmail(String userEmail);
	
	// 카카오 로그인
	public String getKakaoAccessToken(String code);
	public User getKakaoUserInfo(String accessToken);

	// 판매자 정보 등록
	public int writeSeller(Seller seller);
}
