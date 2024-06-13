<script setup>
import { onMounted, ref, watch } from "vue";
import { useProductStore } from "@/stores/product.js";
import { useUserStore } from "@/stores/userStore.js";
import { useRouter } from "vue-router";

const router = useRouter();
const productStore = useProductStore();
const userStore = useUserStore();

onMounted(async () => {
  if (!userStore.loginUser) {
    alert('로그인 후 이용해주세요.');
    router.push('/login');
  }

  await productStore.selectCartList(userStore.loginUser.userId);
});

const cartCnt = ref(0);

watch(() => productStore.cartList, (next) => {
  cartCnt.value = next.length;
}, { deep: true });

const deleteCart = async (cart) => {
  await productStore.deleteCart(cart);
};
</script>

<template>
  <div v-if="productStore.cartList.length === 0">
    <h3>관심 상품이 비어있습니다.</h3>
  </div>
  <div v-else class="cart-page">
    <h3><strong>관심 상품 LIST</strong></h3>
    <h6>💖 <strong>{{ cartCnt }}</strong> 개의 관심 상품 💖</h6>
    <table class="styled-table">
      <thead>
      <tr>
        <th>상품 이미지</th>
        <th>상품 명</th>
        <th>상품 가격</th>
        <th>액션</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(cart, idx) in productStore.cartList" :key="cart.cartId">
        <td>
          <RouterLink :to="`/product/detail/${cart.productCode}`">
            <img :src="`/src/assets/img/product/thumbnail/${cart.productThumbnail}`" alt="준비 중" class="product-thumbnail">
          </RouterLink>
        </td>
        <td>{{ cart.productName }}</td>
        <td>{{ cart.productPrice }} 원</td>
        <td>
          <button class="btn btn-danger" @click="deleteCart(cart)">삭제</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.cart-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'EliceDXNeolli', sans-serif;
  color: #333;
}

h3 {
  margin-top: 20px;
  text-align: center;
  margin-bottom: 20px;
  color: #222;
}
h6 {
  text-align: center;
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

.product-thumbnail {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.btn {
  background-color: #f0786a;
  color: #fff;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: #e74c3c;
}
</style>
