<template>
  <div v-if="show" class="modal-overlay">
    <div class="modal-content">
      <button class="close-btn" @click="closeModal">X</button>
      <h3>검수 상태 변경</h3>
      <select v-model="selectedStatus">
        <option value="검수전">검수전</option>
        <option value="검수중">검수중</option>
        <option value="검수완료">검수완료</option>
      </select>
      <div class="modal-actions">
        <button class="btn" @click="updateStatus">변경</button>
        <button class="btn btn-secondary" @click="closeModal">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  show: Boolean,
  productCode: Number,
  currentStatus: String
});

const emits = defineEmits(['close', 'update']);

const selectedStatus = ref(props.currentStatus);

const closeModal = () => {
  emits('close');
};

const updateStatus = () => {
  emits('update', { productCode: props.productCode, newStatus: selectedStatus.value });
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
  background-color: #f5f5f5;
  font-family: 'EliceDXNeolli', sans-serif;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 90%;
  max-width: 400px;
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

.modal-content h3 {
  margin-bottom: 20px;
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
}

.modal-content select {
  width: 100%;
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.modal-actions {
  display: flex;
  justify-content: space-between;
}

.btn {
  background-color: rgb(36, 208, 255);
  color: #fff;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: rgb(30, 180, 220);
}

.btn-secondary {
  background-color: #ccc;
  color: #333;
}

.btn-secondary:hover {
  background-color: #bbb;
}
</style>
