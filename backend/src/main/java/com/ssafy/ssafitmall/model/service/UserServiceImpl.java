package com.ssafy.ssafitmall.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ssafy.ssafitmall.model.dto.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.ssafitmall.model.dao.UserDao;
import com.ssafy.ssafitmall.model.dto.User;
import com.ssafy.ssafitmall.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserDao userDao, JwtUtil jwtUtil) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User findUserById(String userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    public User loginUser(String userId, String userPwd) {
        User user = userDao.selectUserById(userId);
        if (user == null || user.getUserPwd() == null || !user.getUserPwd().equals(userPwd)) {
            return null;
        }
        return user;
    }

    @Override
    public int writeUser(User user) {
        if (user.getUserPwd() == null || user.getUserPwd().isEmpty()) {
            throw new IllegalArgumentException("패스워드를 입력하세요.");
        }
        return userDao.createUser(user);
    }

    @Override
    public int modifyUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean removeUser(String userId) {
        return userDao.deleteUser(userId) > 0;
    }

    @Override
    public User getUserById(String userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    public String getIdByEmail(String userEmail) {
        return userDao.selectIdByEmail(userEmail);
    }

    @Override
    public String getKakaoAccessToken(String code) {
        String accessToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=a186434f18d2c4c997712f92d10e6888"); // REST API 키
            sb.append("&redirect_uri=http://localhost:5173/user/login/kakao"); // 리다이렉트 URI
            sb.append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            if(responseCode == 200) { // 성공적으로 응답을 받았을 때만 처리
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String result = "";
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                System.out.println("response body : " + result);

                JsonElement element = JsonParser.parseString(result);
                accessToken = element.getAsJsonObject().get("access_token").getAsString();

                br.close();
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                System.out.println("Error response body: " + result);
                throw new IOException("Failed to get Kakao access token, response code: " + responseCode + ", response: " + result);
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get Kakao access token", e);
        }
        return accessToken;
    }

    @Override
    public User getKakaoUserInfo(String accessToken) {
        User user = new User();
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String result = "";
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                System.out.println("response body : " + result);

                JsonElement element = JsonParser.parseString(result);

                JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
                JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

                String nickname = properties.has("nickname") ? properties.get("nickname").getAsString() : "KAKAO_" + System.currentTimeMillis();
                String email = kakaoAccount.has("email") ? kakaoAccount.get("email").getAsString() : null;
                String kakaoId = element.getAsJsonObject().get("id").getAsString();

                // 이메일이 없으면 카카오 ID를 사용
                String userId = email != null ? email : "kakao_" + kakaoId;

                user.setUserId(userId); // 카카오 로그인 사용자의 userId를 설정
                user.setUserPwd(userId); // 액세스 토큰을 패스워드로 사용
                user.setUserName(nickname);
                user.setUserPhone(email != null ? email : "N/A"); // 이메일이 없으면 "N/A"로 설정
                user.setUserEmail(email != null ? email : "N/A"); // 이메일이 없으면 "N/A"로 설정
                user.setUserNickname(nickname);

                System.out.println(user.getUserId());

                // 여기서 유저 정보를 DB에 저장하거나 업데이트합니다.
                User existingUser = userDao.selectUserById(user.getUserId());
                if (existingUser == null) {
                    userDao.createUser(user);
                } else {
                    userDao.updateUser(user);
                }

                br.close();
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                System.out.println("Error response body: " + result);
                throw new IOException("Failed to get Kakao user info, response code: " + responseCode + ", response: " + result);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get Kakao user info", e);
        }

        return user;
    }

    @Override
    public int writeSeller(Seller seller) {
        return userDao.createSeller(seller);
    }


}
