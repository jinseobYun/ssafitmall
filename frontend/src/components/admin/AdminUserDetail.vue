<script setup>
import {useAdminStore} from "@/stores/adminStore.js";
import {onMounted} from "vue";
import {useRoute, useRouter} from "vue-router";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";

const router = useRouter();
const route = useRoute();
const adminStore = useAdminStore()

onMounted(async () => {
    await adminStore.getUser(route.params.userId);
    await adminStore.getUserProductList(route.params.userId)
})
</script>

<template>
  <div class="container">
    <div v-if="!adminStore.userInfo"><LoadingSpinner/></div>
    <div v-else>
      <h2 class="section-title"><strong>{{ adminStore.userInfo["user"].userId }} 회원 관리</strong></h2>

      <h3 class="sub-title">회원 주문 정보</h3>
      <div v-if="adminStore.userInfo['orderInfoList']">
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
          <tbody>
          <tr v-for="(orderInfo, index) in adminStore.userInfo['orderInfoList']" :key="orderInfo.orderNumber">
            <td>{{ orderInfo.orderNumber }}</td>
            <td>{{ adminStore.userInfo['productList'][index].productName }}</td>
            <td>{{ orderInfo.createAt }}</td>
            <td>{{ orderInfo.deliveredAt }}</td>
            <td>{{ orderInfo.deliveryStatus }}</td>
            <td>{{ orderInfo.isDelevered ? "배송완료" : "배송전" }}</td>
          </tr>
          </tbody>
        </table>
      </div>

      <h3 class="sub-title">회원 상품 정보</h3>
      <div v-if="adminStore.userProductList">
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
          <tbody>
          <tr v-for="(product, index) in adminStore.userProductList" :key="product.productCode">
            <td>{{ product.productCode }}</td>
            <td>{{ product.productName }}</td>
            <td>{{ product.productDate }}</td>
            <td>{{ product.inspection }}</td>
            <td>{{ product.isSold }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.section-title {
  text-align: center;
  margin-top: 30px;
}
.container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  font-family: 'EliceDXNeolli', sans-serif;
  color: #333;
}

.section-title {
  font-size: 24px;
  margin-bottom: 20px;
  color: #222;
}

.sub-title {
  font-size: 20px;
  margin-top: 30px;
  margin-bottom: 10px;
  color: #555;
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
  color: #416498;
}
</style>
