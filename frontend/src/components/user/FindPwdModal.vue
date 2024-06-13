<template>
    <div class="modal-overlay" @click.self="close">
      <div class="modal-content">
        <span class="close-btn" @click="close">&times;</span>
        <p>회원님의 비밀번호는 <strong>{{ password }}</strong> 입니다.</p>
      </div>
    </div>
  </template>
  
  <script setup>
  const props = defineProps({
    password: String
  });
  
  const emits = defineEmits(['close']);
  
  const close = () => {
    emits('close');
  };
  </script>
  
  <style scoped>
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 'EliceDXNeolli', sans-serif;
  }
  
  .modal-content {
    background: rgb(232, 232, 232);
    padding: 20px;
    border-radius: 10px;
    width: 90%;
    max-width: 500px;
    max-height: 90vh;
    overflow-y: auto;
    position: relative;
  }
  
  .close-btn {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 20px;
    cursor: pointer;
  }
  
  </style>
  