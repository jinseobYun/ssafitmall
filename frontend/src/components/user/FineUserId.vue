<template>
    <div class="find-container">
        <h2>
        아이디 찾기 페이지 입니다.
        </h2>
        <form @submit.prevent="findId">
            <input type="text" id="email" v-model="userEmail" placeholder="이메일"/>
            <button type="submit">아이디 찾기</button>
        </form>
    </div>    
    <IdModal v-if="showModal" :id="userId" @close="showModal = false"/>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute,useRouter } from "vue-router";
import { useUserStore } from '@/stores/userStore';
import IdModal from './FindIdModal.vue';

const route = useRoute();
const router = useRouter()

const store = useUserStore();
const showModal = ref(false);
const userId = ref('');
const userEmail = ref('');

const findId = async () => {
    try {
        const response = await store.findUserId(userEmail.value);
        userId.value = response;
        showModal.value=true;
    } catch (err) {
        console.error('아이디 찾기 오류:', err);
        alert('아이디 찾기 실패');
  }
}

</script>

<style scoped>
.find-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  text-align: center;
  font-family: 'EliceDXNeolli', sans-serif;
}

.find-container h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 10px;
  font-weight: bold;
}

input[type="text"] {
  width: calc(100% - 20px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  margin-bottom: 20px;
}

button {
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

button:hover {
  background-color: #444;
}

</style>