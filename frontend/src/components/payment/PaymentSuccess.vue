<template>
  <div class="after-order">
    <h2>결제 완료</h2>
    <RouterLink to="/product" class="btn">상품 목록</RouterLink>
    <button @click="moveDetail" class="btn">주문 상세 정보</button>
  </div>
</template>

<script setup>
import {useRoute, useRouter} from "vue-router";
import {useOrderStore} from "@/stores/order.js";
import {onMounted} from "vue";

const route = useRoute();
const router = useRouter();
const store = useOrderStore();

const moveDetail = async () => {
  await store.getOrderInfo(route.params.orderNumber);
  router.push(`/order/orderDetail/${route.params.orderNumber}`);
};

onMounted(() => {
})
</script>

<style scoped>
.after-order {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  text-align: center;
}

.after-order h2 {
  margin-bottom: 20px;
}

.after-order .btn {
  display: inline-block;
  padding: 10px 20px;
  margin: 10px 5px;
  background-color: #009879;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
}

.after-order .btn:hover {
  background-color: #007f5f;
}
</style>
