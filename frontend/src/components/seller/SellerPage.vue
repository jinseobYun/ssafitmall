<template>
  <div class="seller-page">
    <h2 class="title"><strong>판매자 페이지</strong></h2>
    <button @click="openModal('register')" class="btn btn-outline-primary">상품 등록하기</button>
    <div v-if="filteredProducts.length">
      <ul>
        <li v-for="product in filteredProducts" :key="product.productCode">
          <span class="product-info">
            {{ product.productCode }} - {{ product.productName }} - {{ product.productDesc }} - {{ product.productPrice }}원
          </span>
          <button @click="openModal('update', product.productCode)" class="btn btn-outline-secondary">수정</button>
            <button @click="showConfirmDialog(product.productCode)" class="btn btn-outline-danger">삭제</button>
            <Confirm
                v-if="isDialogVisible"
                :message="`상품코드 ${ product.productCode } 상품을 삭제하시겠습니까?`"
                :visible="isDialogVisible"
                @confirm="handleConfirm"
                @cancel="handleCancel"
            />


        </li>
      </ul>
    </div>
    <div v-else>
      <p>등록된 상품이 없습니다.</p>
    </div>

    <!-- Modal -->
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <component :is="modalComponent" :product-code="selectedProductCode" @close="closeModal" @update="updateProduct"></component>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useSellerStore } from "@/stores/sellerStore";
import { useUserStore } from '@/stores/userStore';
import ProductUpdate from './SellerProductUpdate.vue';
import ProductRegister from './SellerProductCreate.vue';
import Confirm from "@/components/common/Confirm.vue";

const store = useSellerStore();
const userStore = useUserStore();
const products = ref([]);
const showModal = ref(false);
const modalComponent = ref(null);
const selectedProductCode = ref(null);



const updateProduct = async ({product,thumbnail,imgs}) => {

  console.log(product)
  await store.editProduct(product.value.productCode,product.value,thumbnail.value,imgs.value).then( async ()=>{
    closeModal()
    const newData = await store.getAllProducts();
    products.value = newData;
  })
}

// 상품 삭제 컨펌 모달 보여줄지 정하는 변수
const isDialogVisible = ref(false);
const selectProduct = ref();
// 상품 삭제 컨펌 모달 보여주는 함수 : 삭제 버튼 클릭시 실행
function showConfirmDialog(productCode) {
  selectProduct.value = productCode;
  isDialogVisible.value = true;
}

//  : 모달 내 확인 버튼 클릭시 실행
async function handleConfirm() {
  isDialogVisible.value = false;
  console.log('확인됨');
  // 삭제 작업을 여기서 수행
  await store.deleteProduct(selectProduct.value)
  selectProduct.value = 0;
  const newData = await store.getAllProducts();
  products.value = newData;
}

// : 모달 내 취소 버튼 클릭시 실행
function handleCancel() {
  isDialogVisible.value = false;
  console.log('취소됨');
}


const filteredProducts = computed(() => {
  return products.value.filter(product => product.userId === userStore.loginUser.userId);
});

onMounted(async () => {
  try {
    const data = await store.getAllProducts();
    products.value = data;
  } catch (error) {
    console.error("상품 목록 로딩 중 오류 발생:", error);
  }
});

const openModal = (type, productCode = null) => {
  if (type === 'register') {
    modalComponent.value = ProductRegister;
  } else if (type === 'update') {
    modalComponent.value = ProductUpdate;
    selectedProductCode.value = productCode;
  }
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  modalComponent.value = null;
  selectedProductCode.value = null;
};
</script>

<style scoped>
.seller-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Noto Sans KR', sans-serif;
  color: #333;
}

.seller-page h2 {
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
  color: #222;
  font-family: 'EliceDXNeolli', sans-serif;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.product-info {
  flex-grow: 1;
  margin-right: 10px;
  font-size: 14px;
  font-family: 'EliceDXNeolli', sans-serif;
}

button {
  margin-left: 10px;
}

.btn {
  padding: 5px 10px;
  border: 1px solid rgb(210, 210, 210);
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-family: 'EliceDXNeolli', sans-serif;
}

.btn-outline-primary {
  margin-bottom: 20px;
  background-color: white;
  color: #007bff;
  border-color: #007bff;
}

.btn-outline-primary:hover {
  background-color: #007bff;
  color: white;
}

.btn-outline-secondary {
  background-color: white;
  color: #6c757d;
  border-color: #6c757d;
}

.btn-outline-secondary:hover {
  background-color: #6c757d;
  color: white;
}

.btn-outline-danger {
  background-color: white;
  color: #dc3545;
  border-color: #dc3545;
}

.btn-outline-danger:hover {
  background-color: #dc3545;
  color: white;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);

}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
  position: relative;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 20px;
  cursor: pointer;
}
</style>
