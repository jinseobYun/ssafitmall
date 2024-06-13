<script setup>
import { useAdminStore } from "@/stores/adminStore.js";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import { useUserStore } from "@/stores/userStore.js";

const router = useRouter();
const route = useRoute();
const adminStore = useAdminStore();
const userStore = useUserStore();

onMounted(async () => {
  if (!userStore.loginUser) {
    alert('로그인 후 이용하세요');
    router.push('/login');
  }
  await adminStore.getUser(userStore.loginUser.userId);
  await adminStore.getUserProductList(userStore.loginUser.userId);
});
</script>

<template>
  <div class="admin-page">
    <div v-if="!adminStore.userInfo">
      <LoadingSpinner />
    </div>
    <div v-else>
      <h2>{{ adminStore.userInfo["user"].userId }} 회원 관리</h2>

      <div >
        <h4>회원 주문 정보</h4>
        <table class="styled-table">
          <thead>
            <tr>
              <th>주문 번호</th>
              <th>상품명</th>
              <th>주문 일자</th>
              <th>배송 완료 일</th>
              <th>주문 현황</th>
              <th>배송 현황</th>
            </tr>
          </thead>
          <tbody v-if="adminStore.userInfo['orderInfoList']">
            <tr v-for="(orderInfo, index) in adminStore.userInfo['orderInfoList']" :key="orderInfo.orderNumber">
              <td>{{ index + 1 }}</td>
              <td><RouterLink :to="`/order/orderDetail/${orderInfo.orderNumber}`">{{ adminStore.userInfo['productList'][index].productName }}</RouterLink></td>
              <td>{{ orderInfo.createAt }}</td>
              <td>{{ orderInfo.deliveredAt }}</td>
              <td>{{ orderInfo.deliveryStatus }}</td>
              <td>{{ orderInfo.isDelevered ? "배송완료" : "배송전" }}</td>
            </tr>
          </tbody>
          <tbody v-else>
          <tr><td>주문 정보가 존재하지 않습니다.</td></tr>
          </tbody>
        </table>
      </div>

      <div v-if="adminStore.userProductList">
        <h4>회원 상품 정보</h4>
        <table class="styled-table">
          <thead>
            <tr>
              <th>상품 번호</th>
              <th>상품명</th>
              <th>등록 일자</th>
              <th>검수 상태</th>
              <th>거래 상태</th>
            </tr>
          </thead>
          <tbody v-if="adminStore.userProductList.length">
            <tr v-for="(product, index) in adminStore.userProductList" :key="product.productCode">
              <td>{{ product.productCode }}</td>
              <td>{{ product.productName }}</td>
              <td>{{ product.productDate }}</td>
              <td>{{ product.inspection }}</td>
              <td>{{ product.isSold }}</td>
            </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td>등록된 상품이 없습니다.</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<style scoped>
.container {
  display: flex;
  justify-content: space-evenly;
  align-items: flex-start;
  min-height: 80vh;
  padding-top: 50px;
  padding-bottom: 50px;
  background-color: #f0f2f5;
}

.admin-page {
  width: 100%;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  font-family: 'EliceDXNeolli', sans-serif;
  padding: 20px;
}

.admin-page h2 {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-top: 20px;
  margin-bottom: 20px;
  color: #000000;
}
.styled-table{
  color: #000000;
}

.admin-page h4 {
  margin-top: 30px;
  margin-bottom: 15px;
  font-size: 20px;
  font-weight: bold;
  color: #000000;
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 16px;
  text-align: left;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
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
</style>
