<template>
  <div v-if="visible" class="dialog-overlay" @keydown.enter="confirm">
    <div class="dialog">
      <p>{{ message }}</p>
      <button @click="confirm">확인</button>
      <button @click="cancel">취소</button>
    </div>
  </div>
</template>

<script setup>
import { defineEmits, onMounted, onBeforeUnmount } from 'vue';

const props = defineProps({
  message: {
    type: String,
    required: true
  },
  visible: {
    type: Boolean,
    required: true
  },


});

const emit = defineEmits(['confirm', 'cancel']);


function confirm() {
    emit('confirm');
}

function cancel() {
  emit('cancel');
}

function handleKeydown(event) {
  if (event.key === 'Enter') {
    confirm();
  }
}

onMounted(() => {
  window.addEventListener('keydown', handleKeydown);
});

onBeforeUnmount(() => {
  window.removeEventListener('keydown', handleKeydown);
});
</script>

<style scoped>
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
.dialog {
  border: 3px solid rgba(0, 0, 0, 0.5);
  background: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
}
button {
  margin: 5px;
}
</style>
