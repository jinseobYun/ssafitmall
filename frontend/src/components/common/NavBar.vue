<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light w-100">
      <div class="container-fluid">
        <!-- 홈으로 돌아오기 -->
        <div class="header-logo">
          <RouterLink class="navbar-brand" to="/">
            <img src="/src/assets/img/logo/logo.jpg" alt="Logo" class="logo-img" />
          </RouterLink>
        </div>

        <!-- 검색 바 -->
        <div class="d-flex search-bar-container mx-auto" >
          <input class="form-control me-2" id="searchbar" type="search"
                 placeholder="검색" aria-label="Search" v-model="keyword" @keydown.enter="searchProductList">
          <button class="btn btn-outline-dark" @click="searchProductList">검색</button>
        </div>

        <!-- 로그인/회원가입 버튼 -->
        <div class="d-flex ms-auto">
          <span v-if="store.loginUser" id="login-success" class="d-flex align-items-center">
            <RouterLink class="nav-link" to="/cart">
              <button type="button" class="btn btn-outline-dark">관심 상품</button>
            </RouterLink>
            <RouterLink class="nav-link" to="/mypage">
              <button type="button" class="btn btn-outline-dark">마이페이지</button>
            </RouterLink>
            <button class="btn btn-outline-secondary" @click="logout" id="logout">로그아웃</button>
            <span v-if="store.loginUser.userRole === 'admin'">
              <RouterLink class="nav-link" to="/adminpage/userList">
                <button type="button" class="btn btn-outline-dark">관리자 페이지</button>
              </RouterLink>
            </span>
            <span v-if="store.loginUser.userRole === 'seller'">
              <RouterLink class="nav-link" to="/sellerpage">
                <button type="button" class="btn btn-outline-dark">판매자 페이지</button>
              </RouterLink>
            </span>
          </span>
          <span v-else class="d-flex align-items-center">
            <RouterLink class="nav-link" to="/regist">
              <button type="button" class="btn btn-outline-dark">회원가입</button>
            </RouterLink>
            <RouterLink class="nav-link" to="/login">
              <button type="button" class="btn btn-outline-dark">로그인</button>
            </RouterLink>
          </span>
        </div>
      </div>
    </nav>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/userStore";
import {useRoute, useRouter} from "vue-router";
import {useProductStore} from "@/stores/product.js";
import ProductList from "@/components/product/ProductList.vue";
import {ref, watch} from "vue";


const route = useRoute();
const router = useRouter()
const productStore = useProductStore();
const store = useUserStore();
const keyword = ref(productStore.searchParams.keyword);


watch(keyword, (next,prev)=>{

  productStore.searchParams.keyword = next
})
const searchProductList = async () => {
  await productStore.getProductList();
  router.push("/product/searchResult");
}

const logout = () => {
  store.logout();
};
</script>

<style>
.navbar {
  width: 100%;
  font-family: 'EliceDXNeolli', sans-serif;
}

#searchbar {
  width: 300px;
}

.logo-img {
  height: 100px; /* 로고 이미지 크기 조절 */
}

.navbar, .nav-link, .btn {
  font-family: 'EliceDXNeolli', sans-serif;
}

.search-bar-container {
  flex-grow: 30;
  justify-content: center;
}
</style>
