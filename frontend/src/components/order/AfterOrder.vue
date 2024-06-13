<template>
  <div class="after-order">
    <h2>결제 완료</h2>
    <RouterLink to="/product" class="btn-list">상품 목록</RouterLink>
    <button @click="moveDetail" class="btn">주문 상세 정보</button>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { useOrderStore } from "@/stores/order.js";
import {onMounted} from "vue";

const route = useRoute();
const router = useRouter();
const store = useOrderStore();

const moveDetail = async () => {
  await store.getOrderInfo(route.params.orderNumber);
  router.push(`/order/orderDetail/${route.params.orderNumber}`);
};

onMounted(()=>{
  moveDetail()
})
</script>

<style scoped>
.after-order {
  max-width: 600px;
  margin: 0 auto;
  margin-top: 100px;
  padding: 20px;
  background-color: #eeeeee;
  border-radius: 8px;
  text-align: center;
  font-family: 'EliceDXNeolli', sans-serif;
}

h3 {
  margin-top: 30px;
  margin-bottom: 30px;
}

.after-order .btn {
  display: inline-block;
  padding: 10px 20px;
  margin: 10px 5px;
  background-color: rgb(36, 208, 255);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
}
.btn-list {
  display: inline-block;
  padding: 10px 20px;
  margin: 10px 5px;
  background-color: #ababab;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  
}
</style>
