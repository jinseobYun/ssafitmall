// userStore.js
import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { useProductStore } from "@/stores/product.js";

const USER_API_URL = `http://localhost:8080/user`;

export const useUserStore = defineStore("user", () => {
  const loginUser = ref(null);
  const productStore = useProductStore();

  const setUser = (user) => {
    loginUser.value = user;
    sessionStorage.setItem('user-role', user.userRole); // 사용자 역할 저장
  };

  const userLogin = (userId, userPwd) => {
    axios.post(`${USER_API_URL}/login`, {
      userId: userId,
      userPwd: userPwd,
    }).then((res) => {
      sessionStorage.setItem('access-token', res.data["access-token"]);
      const token = res.data["access-token"].split(".");
      const resUser = JSON.parse(atob(token[1]));
      loginUser.value = resUser;

    }).then(() => {
      productStore.selectCartList(loginUser.value.userId);
      if (loginUser.value.userRole === 'seller') {
        router.push({ name: 'sellerpage' });
      } else if (loginUser.value.userRole === 'admin') {
        router.push({ name: 'adminUserList' });
      } else {
        router.push({ name: 'home' });
      }
    }).catch((err) => {
      alert('아이디 또는 비밀번호를 확인하세요.');
      console.error("로그인 실패:", err);
    });
  };

  const createUser = (userId, userEmail, userPwd, userName, userPhone, userNickname) => {
    const user = {
      userId: userId,
      userEmail: userEmail,
      userPwd: userPwd,
      userName: userName,
      userPhone: userPhone,
      userNickname: userNickname,
    };
    axios.post(`${USER_API_URL}/signup`, user)
      .then(() => {
        alert("회원가입을 축하드립니다.");
        router.push({ name: 'login' });
      }).catch((err) => {
        alert("회원가입에 실패했습니다.");
        console.error("회원가입 실패:", err);
      });
  };

  const logout = () => {
    sessionStorage.removeItem("access-token");
    loginUser.value = null;
    router.push('/');
  };

  const getUser = (userId) => {
    return axios.get(`${USER_API_URL}/${userId}`).then((res) => {
      return res.data;
    }).catch((err) => {
      console.error("유저 정보 가져오기 실패:", err);
    });
  };

  const editUser = (user) => {
    return axios.put(`${USER_API_URL}/${user.userId}`, user);
  };

  const deleteUser = (userId) => {
    return axios.delete(`${USER_API_URL}/${userId}`);
  };

  const findUserId = async (userEmail) => {
    try {
      const response = await axios.get(`${USER_API_URL}/findid/${userEmail}`);
      return response.data;
    } catch (error) {
      console.error("아이디 찾기 실패:", error);
      throw new Error('아이디 찾기 실패');
    }
  };

  const findUserPwd = async (userEmail, userId) => {
    try {
      const response = await axios.get(`${USER_API_URL}/findpwd/${userEmail}/${userId}`);
      return response.data;
    } catch (err) {
      console.error("비밀번호 찾기 실패:", err);
      throw new Error('비밀번호 찾기 실패');
    }
  };
  const kakaoLogin = async (code) => {
    try {
      const response = await axios.get(`${USER_API_URL}/login/kakao?code=${code}`);
      const data = response.data;
  
      // 사용자 정보를 Pinia 스토어에 저장
      setUser(data.user);
  
      // JWT 토큰을 저장 (선택적)
      localStorage.setItem('access-token', data['access-token']);
  
      alert(`로그인 성공: ${data.user.userName}`);
      router.push('/');
    } catch (error) {
      console.error('카카오 로그인 실패:', error);
      alert('카카오 로그인 실패');
      router.push('/login');
    }
  };

  const saveSellerInfo = (seller) => {
    return axios.post(`${USER_API_URL}/seller`,seller).then().catch((err) => {
      console.log("판매자 정보 입력 에러");
      console.log(err);
    })
  }


  return { 
    createUser, 
    userLogin, 
    logout, 
    getUser, 
    loginUser, 
    editUser, 
    deleteUser, 
    findUserId, 
    findUserPwd, 
    setUser, 
    kakaoLogin ,
    saveSellerInfo
  };
});
