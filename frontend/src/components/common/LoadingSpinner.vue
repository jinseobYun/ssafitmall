<template>
  <div class="spinner-overlay">
    <div class="spinner"></div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.spinner-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5); /* 배경을 반투명하게 */
  z-index: 9999; /* 최상단에 위치 */
}

.spinner {
  border: 8px solid rgba(255, 255, 255, 0.3); /* 가벼운 회색 테두리 */
  border-top: 8px solid white; /* 흰색의 상단 테두리 */
  border-radius: 50%;
  width: 60px;
  height: 60px;
  animation: spin 1s linear infinite; /* 스피너 애니메이션 */
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
