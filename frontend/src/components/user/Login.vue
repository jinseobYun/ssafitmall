<template>
  <div class="login-container">
    <h2>LOGIN</h2>
    <fieldset>
      <div class="input-group">
        <input type="text" id="userId" v-model="userId" placeholder="아이디" />
      </div>
      <div class="input-group">
        <input type="password" id="userPwd" v-model="userPwd" placeholder="비밀번호" />
      </div>
      <div class="input-group checkbox-group">
        <input type="checkbox" id="remember-check">
        <label for="remember-check">로그인 상태 유지</label>
      </div>
      <div class="input-group">
        <button @click="login">LOGIN</button>
      </div>
    </fieldset>
    <div class="links">
      <a @click="regist">회원가입</a> | <a @click="fineUserID">아이디찾기</a> | <a @click="fineUserPwd">비밀번호찾기</a>
    </div>
    <div class="social-login">
      <button class="kakao-login" @click="kakaologin">카카오로 간편로그인</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

const router = useRouter();
const userStore = useUserStore();
const userId = ref('');
const userPwd = ref('');

const login = async () => {
  try {
    await userStore.userLogin(userId.value, userPwd.value);
    const userRole = sessionStorage.getItem('user-role'); // 저장된 역할 정보 가져오기

    if (userRole === 'seller') {
      router.push({ name: 'sellerpage' });
    } else {
      router.push({ name: 'home' }); // 판매자가 아닌 경우 홈 페이지로 리다이렉션
    }
  } catch (error) {
    console.error('로그인 중 오류 발생:', error);
    alert('로그인 실패');
  }
};

const regist = () => {
  router.push(`/regist`)
}

const fineUserID = () => {
  router.push('/finduserid')
}

const fineUserPwd = () => {
  router.push('/finduserpwd')
}


const kakaologin = () => {
  // 카카오 SDK 초기화
  Kakao.init('1b877e7c2e5ebb465729bd17c935063c'); // JavaScript 키 사용

  // 로그인 처리
  Kakao.Auth.authorize({
    redirectUri: 'http://localhost:5173/user/login/kakao'
  });
}

</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  text-align: center;
  font-family: 'EliceDXNeolli', sans-serif;
}

.login-container h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.fieldset {
  border: none;
  padding: 0;
  margin: 0;
}

.input-group {
  margin-bottom: 15px;
}

.input-group input[type="text"],
.input-group input[type="password"] {
  width: calc(100% - 20px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  margin: 0 auto;
}

.checkbox-group {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 15px;
}

.input-group button {
  width: 100%;
  padding: 10px;
  background-color: #000;
  border: none;
  border-radius: 5px;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.input-group button:hover {
  background-color: #444;
}

.links {
  margin-top: 20px;
}

.links a {
  color: #000;
  text-decoration: none;
  margin: 0 10px;
}

.links a:hover {
  text-decoration: underline;
}

.social-login {
  margin-top: 20px;
}

.kakao-login {
  width: 100%;
  padding: 10px;
  background-color: #fee500;
  border: none;
  border-radius: 5px;
  color: #000;
  font-size: 16px;
  cursor: pointer;
}
</style>
