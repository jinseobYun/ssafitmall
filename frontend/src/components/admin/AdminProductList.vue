<template>
  <div class="admin-page">
    <h2 class="title"><strong>관리자 페이지</strong></h2>
    <select v-model="inspection" class="role-select">
      <option value="ALL">ALL</option>
      <option value="검수전">검수전</option>
      <option value="검수중">검수중</option>
      <option value="검수완료">검수완료</option>
    </select>

    <div v-if="adminStore.productList && adminStore.productList.length > 0">
      <table class="styled-table">
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Date</th>
            <th>Category</th>
            <th>Id</th>
            <th>Sell</th>
            <th>Check</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in paginatedProducts" :key="product.productCode">
            <td>{{ product.productCode }}</td>
            <td><RouterLink :to="`/product/detail/${product.productCode}`">{{ product.productName }}</RouterLink></td>
            <td>{{ product.productDate }}</td>
            <td>{{ product.categoryCode }}</td>
            <td>{{ product.userId }}</td>
            <td>{{ product.isSold }}</td>
            <td><button @click="openModal(product)" class="btn">{{ product.inspection }}</button></td>
            <td>
              <button @click="openSoldModal(product)" class="btn btn-small">상품 수정</button>
              <AdminUpdateProductModal v-if="isShow" :product="selectedProduct" @close="closeSoldModal" @update="updateProduct"/>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button class="btn" :disabled="currentPage === 1" @click="prevPage">이전</button>
        <span>{{ currentPage }} / {{ totalPages }}</span>
        <button class="btn" :disabled="currentPage === totalPages" @click="nextPage">다음</button>
      </div>
    </div>
    <div v-else>
      <h3>등록된 상품이 없습니다.</h3>
    </div>

    

    <InspectionModal
      :show="showModal"
      :productCode="selectedProductCode"
      :currentStatus="currentStatus"
      @close="closeModal"
      @update="updateInspectionStatus"
    />
  </div>
</template>


<script setup>
import { onMounted, ref, watch, computed } from "vue";
import { useAdminStore } from "@/stores/adminStore.js";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import InspectionModal from "@/components/admin/InspectionModal.vue";
import AdminUpdateProductModal from "@/components/admin/AdminUpdateProductModal.vue";
import {useProductStore} from "@/stores/product.js";


const adminStore = useAdminStore()
const productStore = useProductStore()
const inspection = ref('ALL')

import { RouterLink } from "vue-router";


// 검수 상태 모달 설정
const showModal = ref(false);
const selectedProductCode = ref(null);
const currentStatus = ref("");

// 검수 상태 모달 창 보이게
const openModal = (product) => {
  selectedProductCode.value = product.productCode;
  currentStatus.value = product.inspection;
  showModal.value = true;
};

// 검수 상태 모달창 닫기
const closeModal = () => {
  showModal.value = false;
};

// 상품 검수 상태 변경
const updateInspectionStatus = ({ productCode, newStatus }) => {
  adminStore.updateInspectionStatus(productCode, newStatus).then(() => {
    adminStore.getProductList(inspection.value);
    closeModal();
  });
};

// 상품 판매 상태 모달
const isShow = ref(false);
const selectedProduct = ref(null);

const openSoldModal = (product) => {
  selectedProduct.value = product;
  isShow.value = true;
};

// 검수 상태 모달창 닫기
const closeSoldModal = async (isDelete) => {
  if(isDelete){
    await adminStore.getProductList(inspection.value);
  }
  isShow.value = false;
};

const updateProduct = ({ updateProduct }) => {
  adminStore.updateProduct(updateProduct).then(() => {
    closeSoldModal();
  });
};

// 페이지네이션 관련 변수 및 함수
const currentPage = ref(1);
const itemsPerPage = 6;

watch(inspection,(next,prev)=>{
  console.log(next)
  adminStore.getProductList(next)
})

const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return adminStore.productList.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil(adminStore.productList.length / itemsPerPage);
});

const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--;
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) currentPage.value++;
};


onMounted(() => {
  adminStore.getProductList(inspection.value);
});
</script>

<style scoped>
.admin-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Noto Sans KR', sans-serif;
  color: #333;
}

.admin-page h2 {
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
  color: #222;
  font-family: 'EliceDXNeolli', sans-serif;
}

.btn {
  border: 1px solid rgb(210, 210, 210);
  background-color: white;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
  font-family: 'EliceDXNeolli', sans-serif;
}

.btn:hover {
  background-color: rgb(200, 200, 200);
}

.btn-small {
  padding: 5px 10px;
}

.btn-danger {
  background-color: #e74c3c;
  color: #fff;
}

.role-select {
  margin-bottom: 20px;
  padding: 5px;
  border-radius: 5px;
  font-family: 'EliceDXNeolli', sans-serif;
}


.styled-table {
  width: 100%;
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 16px;
  text-align: left;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.styled-table thead tr {
  background-color: rgb(36, 208, 255);
  color: #ffffff;
  text-align: left;
  font-weight: bold;
}

.styled-table th, .styled-table td {
  padding: 12px 15px;
}

.styled-table tbody tr {
  border-bottom: 1px solid #dddddd;
}

.styled-table tbody tr:nth-of-type(even) {
  background-color: #f3f3f3;
}

.styled-table tbody tr:last-of-type {
  border-bottom: 2px solid rgb(36, 208, 255);
}

.styled-table tbody tr.active-row {
  font-weight: bold;
  color: rgb(36, 208, 255);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.pagination .btn {
  margin: 0 5px;
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
