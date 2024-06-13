<template>
  <div class="signup-container">
    <h2>SIGN UP</h2>
    <fieldset>
      <div class="input-group">
        <input type="text" id="userId" v-model="userId" placeholder="아이디" />
      </div>
      <div class="input-group">
        <input type="password" id="userPwd" v-model="userPwd" placeholder="비밀번호" />
      </div>
      <div class="input-group">
        <input type="password" id="userPwdConfirm" v-model="userPwdConfirm" placeholder="비밀번호 확인" />
      </div>
      <div class="input-group">
        <input type="text" id="userName" v-model="userName" placeholder="이름" />
      </div>
      <div class="input-group">
        <input type="text" id="userPhone" v-model="userPhone" placeholder="전화번호" />
      </div>
      <div class="input-group">
        <input type="text" id="userEmail" v-model="userEmail" placeholder="이메일" />
      </div>
      <div class="input-group">
        <input type="text" id="userNickname" v-model="userNickname" placeholder="닉네임" />
      </div>
      <div class="input-group checkbox-group">
        <input type="checkbox" id="agree-terms">
        <label for="agree-terms">이용약관에 동의합니다</label>
      </div>
      <div class="input-group">
        <button @click="signup">회원가입</button>
      </div>
    </fieldset>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

const router = useRouter();
const userStore = useUserStore();



const userId = ref('');
const userEmail = ref('');
const userPwd = ref('');
const userPwdConfirm = ref('');
const userName = ref('');
const userPhone = ref('');
const userNickname = ref('');

const signup = async () => {
  if (userPwd.value !== userPwdConfirm.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }

  try {
    await userStore.createUser(userId.value, userEmail.value, userPwd.value, userName.value, userPhone.value, userNickname.value);
    router.push('/login'); // 회원가입 후 로그인 페이지로 이동
  } catch (error) {
    console.error('회원가입 중 오류 발생:', error);
    alert('회원가입 실패');
  }
};
</script>

<style scoped>
.signup-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  text-align: center;
  font-family: 'EliceDXNeolli', sans-serif;
}

.signup-container h2 {
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
.input-group input[type="email"],
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

.social-signup {
  margin-top: 20px;
}

.kakao-signup {
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
