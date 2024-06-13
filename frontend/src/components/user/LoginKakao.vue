<template>
    <div>
      <p>카카오 로그인 중...</p>
    </div>
  </template>
  
  <script setup>
  import { onMounted } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  import axios from 'axios';
  import { useUserStore } from '@/stores/userStore';
  
  const router = useRouter();
  const route = useRoute();
  const userStore = useUserStore();
  
  onMounted(async () => {
    const code = route.query.code;
    if (code) {
      try {
        await userStore.kakaoLogin(code);
      } catch (error) {
        console.error('카카오 로그인 실패:', error);
      }
    }
  });
  </script>
  