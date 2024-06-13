<template>
  <div v-if="!store.product"></div>
  <div v-else class="order-info">
    <h3 class="header">상품 확인</h3>
    <hr />

    <table class="order-table">
      <thead>
        <tr>
          <th>상품 이미지</th>
          <th>상품 이름</th>
          <th>상품 가격</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <img :src="`/src/assets/img/product/thumbnail/${store.product.productThumbnail}`" class="product-thumbnail"/>
          </td>
          <td>
            <RouterLink :to="`/product/detail/${store.product.productCode}`">
              <p>{{ store.product.productName }}</p>
            </RouterLink>
          </td>
          <td>
            <p>{{ store.product.productPrice }}원</p>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="button">
      <RouterLink :to="`/order/generate/${route.params.productCode}`" class="btn btn-primary">주문 하기</RouterLink>

      <button @click="insertCart" class="btn btn-secondary">관심 상품 등록</button>
    </div>

    <hr />
  </div>
</template>

<script setup>
import { useProductStore } from "@/stores/product";
import { useRoute,useRouter } from "vue-router";
import { onMounted } from "vue";
import {useUserStore} from "@/stores/userStore.js";
const router = useRouter()
const route = useRoute();
const userStore = useUserStore()
const store = useProductStore();



const insertCart = () => {
  store.addCart(route.params.productCode);
};

onMounted(async () => {

  if(!userStore.loginUser) {
    alert('로그인 후 이용 해주세요')
    router.push("/login");
  }
  if (!store.product) {
    await store.getProduct(route.params.productCode);
  }
});
</script>

<style scoped>
.header {
  margin-top: 90px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-weight: 600;
}

.order-info{
  margin: auto;
  font-family: 'EliceDXNeolli', sans-serif;
}

.order-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 50px;
  margin-bottom: 50px;
}

.order-table th, .order-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

.order-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.product-thumbnail {
  width: 100px;
  height: auto;
}
.button {
  margin-top: 90px;
  text-align: center;
  flex-direction: row;
  justify-content: space-between;
  font-weight: 600;
}
</style>
