<template>
  <div v-if="orderStore.loading">
    <LoadingSpinner/>
  </div>
  <div v-else-if="!orderStore.orderInfo || !productStore.product">
    <h4>주문 정보를 불러오는데 실패했습니다. 다시 불러오시겠습니까?</h4>
    <button @click="loadOrderInfo" class="btn">예</button>
    <button @click="()=>{router.push('/')}">메인으로</button>
  </div>
  <div v-else class="order-detail">
    <h3><strong>주문 상세 페이지</strong></h3>
    <table>
      <thead>
        <tr>
          <th>상품 번호</th>
          <th>상품 이미지</th>
          <th>상품명</th>
          <th>받는 사람</th>
          <th>주문 일자</th>
          <th>결제 현황</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{{ orderStore.orderInfo.orderNumber }}</td>
          <td>
            <img :src="`/src/assets/img/product/thumbnail/${productStore.product.productThumbnail}`" />
          </td>
          <td>{{ productStore.product.productName }}</td>
          <td>{{ orderStore.orderInfo.receiverName }}</td>
          <td>{{ orderStore.orderInfo.createAt }}</td>
          <td>{{orderStore.orderInfo.deliveryStatus}}</td>
        </tr>
      </tbody>
    </table>
    <button v-if="orderStore.orderInfo.deliveryStatus ==='결제대기'" @click="preparePayment(orderStore.orderInfo,productStore.product)" class="btn btn-primary">결제 하기</button>
    <button v-else @click="deletePayment(productStore.product.productCode,orderStore.orderInfo)" class="btn btn-danger">결제 취소</button>
    <button @click="modifyOrder" class="btn">주문 수정</button>
    <button @click="deleteOrder()" class="btn btn-danger">주문 취소</button>
  </div>
</template>

<script setup>
import { useOrderStore } from "@/stores/order";
import { useProductStore } from "@/stores/product";
import { useRoute, useRouter } from "vue-router";
import {computed, onMounted, ref} from "vue";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import { usePaymentStore } from '@/stores/paymentStore.js';
import Modal from "@/components/common/Modal.vue";

const paymentStore = usePaymentStore();
const router = useRouter();
const orderStore = useOrderStore();
const productStore = useProductStore();
const route = useRoute();


const deleteOrder = () =>{
  var confirm = window.confirm('정말 주문을 취소하시겠습니까?')
  if (confirm){
    if(orderStore.orderInfo.deliveryStatus !== '결제대기'){
      alert('결제 취소후 다시 시도하세요')
    }else{

  orderStore.deleteOrder(route.params.orderNumber)
    }
  }
}
const deletePayment = (productCode,order) => {
  var confirm = window.confirm('정말 결제를 취소하시겠습니까?')
  if (confirm) {
    paymentStore.cancelPayment(productCode, order)
  }
}

const preparePayment = async (order,product) => {
   paymentStore.preparePayment(order,product)
}

const modifyOrder = () => {
  router.push(`/order/orderUpdate/${route.params.orderNumber}`);
};

const loadOrderInfo = async () => {
  await orderStore.getOrderInfo(route.params.orderNumber);
  await productStore.getProduct(orderStore.orderInfo.productCode);
};

onMounted(async () => {
    await orderStore.getOrderInfo(route.params.orderNumber);
    await productStore.getProduct(orderStore.orderInfo.productCode);
});
</script>
<style scoped>
h3 {
  text-align: center;
  margin-top: 30px;
  margin-bottom: 30px;
} 
.order-detail {
  max-width: 800px;
  margin: 50px auto;  /* 페이지 상단과 더 가까워지도록 조정 */
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  
  font-family: 'EliceDXNeolli', sans-serif;
}

.order-detail table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.order-detail th, .order-detail td {
  border: 1px solid #ddd;
  padding: 8px;
}

.order-detail th {
  background-color: rgb(36, 208, 255);
  color: white;
  text-align: left;
}

.order-detail img {
  max-width: 100px;
  height: auto;
}

.order-detail .btn {
  display: inline-block;
  padding: 10px 20px;
  margin: 10px 5px;
  background-color: rgb(36, 208, 255);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
}

.order-detail .btn:hover {
  background-color: rgb(36, 208, 255);
}

.order-detail .btn-danger {
  background-color: #e74c3c;
}

.order-detail .btn-danger:hover {
  background-color: #c0392b;
}


</style>
