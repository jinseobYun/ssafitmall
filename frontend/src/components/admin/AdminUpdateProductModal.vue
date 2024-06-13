<script setup>
import { useAdminStore } from "@/stores/adminStore.js";
import { ref } from "vue";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ConfirmDialog from '@/components/common/Confirm.vue';


const adminStore = useAdminStore()

const props = defineProps({
  product: Object
});

const updateProduct = ref(props.product);

const emits = defineEmits(['close', 'update']);

const closeModal = () => {
  emits('close');
};

const updateStatus = () => {
  emits('update', { updateProduct });
};


const isDialogVisible = ref(false);

function showConfirmDialog() {
  isDialogVisible.value = true;
}

async  function handleConfirm() {
  isDialogVisible.value = false;
  console.log('확인됨');
  await adminStore.deleteProduct(props.product.productCode)
  emits('close',{isDelete:true});
}

function handleCancel() {
  isDialogVisible.value = false;

  console.log('취소됨');
}


</script>

<template>
  <div class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <button class="close-btn" @click="closeModal">X</button>
      <div v-if="!updateProduct">
        <LoadingSpinner />
      </div>
      <div v-else>
        <label for="pName">상품 명</label>
        <input id="pName" type="text" v-model="updateProduct.productName" />
        <label for="price">가격</label>
        <input id="price" type="number" v-model="updateProduct.productPrice" />
        <label for="desc">상품 설명</label>
        <textarea id="desc" name="desc" cols="30" rows="10" v-model="updateProduct.productDesc"></textarea>

        <div class="radio-group">
          <input type="radio" v-model="updateProduct.categoryCode" id="option1" value="ORG" />
          <label for="option1">유기농</label>
          <input type="radio" v-model="updateProduct.categoryCode" id="option2" value="GAI" />
          <label for="option2">게이너</label>
          <input type="radio" v-model="updateProduct.categoryCode" id="option3" value="PRO" />
          <label for="option3">프로틴</label>
          <input type="radio" v-model="updateProduct.categoryCode" id="option4" value="ENE" />
          <label for="option4">에너지</label>
          <input type="radio" v-model="updateProduct.categoryCode" id="option5" value="AMI" />
          <label for="option5">아미노산</label>
          <input type="radio" v-model="updateProduct.categoryCode" id="option6" value="DIET" />
          <label for="option6">다이어트</label>
          <input type="radio" v-model="updateProduct.categoryCode" id="option7" value="NUT" />
          <label for="option7">영양제</label>
          <input type="radio" v-model="updateProduct.categoryCode" id="option8" value="SHA" />
          <label for="option8">쉐이커</label>
          <input type="radio" v-model="updateProduct.categoryCode" id="option9" value="HEA" />
          <label for="option9">헬스용품</label>
        </div>

        <input type="text" v-model="updateProduct.userId" />
        <select v-model="updateProduct.isSold">
          <option value="판매중">판매중</option>
          <option value="거래중">거래중</option>
          <option value="판매완료">판매완료</option>
        </select>
        <select v-model="updateProduct.inspection">
          <option value="검수전">검수전</option>
          <option value="검수중">검수중</option>
          <option value="검수완료">검수완료</option>
        </select>
        <button class="btn" @click="updateStatus">수정</button>
        <button  class="btn-danger" @click="showConfirmDialog">삭제</button>
        <ConfirmDialog
              v-if="isDialogVisible"
              :message="'정말 삭제하시겠습니까?'"
              :visible="isDialogVisible"
              @confirm="handleConfirm"
              @cancel="handleCancel"
        />

      </div>
    </div>
  </div>
</template>

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

.modal-content label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

.modal-content input,
.modal-content textarea,
.modal-content select {
  width: 100%;
  margin-bottom: 15px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.radio-group {
  display: flex;
  vertical-align: middle;
  
  gap: 10px;
  margin-bottom: 15px;
}

.radio-group input {
  margin-right: 5px;
}

.radio-group label {
  margin-right: 20px;
}

.modal-content .btn {
  background-color: rgb(36, 208, 255);
  color: #fff;
  border: none;
  padding: 10px 20px;
  margin-left: 20px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}


.modal-content .btn-danger {
  background-color: #f0786a;
  color: #fff;
  margin-left: 20px;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.modal-content .btn:hover {
  background-color: rgb(30, 180, 220);
}
</style>
