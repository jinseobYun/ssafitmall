<template>
  <div class="app-container">
    <header>
      <div class="banner">
        <img src="/src/assets/img/banner/배너.jpg" class="d-block w-100" alt="...">
      </div>
      <NavBar class="nav" :key="userStore.userLogin" />
      <ProductCategory />
      <router-view />
    </header>
    <footer class="footer">
      <div class="container">
        <div class="row">
          <!-- About Section -->
          <div class="col-md-4">
            <h5 class="text-uppercase">About SSAFitMall</h5>
            <p>SSAFitMall is your one-stop shop for all health and fitness products. From gym equipment to supplements, we provide everything you need to stay fit and healthy.</p>
          </div>
          <!-- Contact Info -->
          <div class="col-md-4">
            <h5 class="text-uppercase">Contact Us</h5>
            <ul class="list-unstyled">
              <li>Email: info@ssafitmall.com</li>
              <li>Phone: +123-456-7890</li>
              <li>Address: 123 Fit Street, Health City, Wellness State</li>
            </ul>
          </div>
          <div class="col-md-4">
            <h5 class="text-uppercase">Follow Us</h5>
            <ul class="list-unstyled">
              <li><a href="https://facebook.com" class="text-black">Facebook</a></li>
              <li><a href="https://instagram.com" class="text-black">Instagram</a></li>
              <li><a href="https://twitter.com" class="text-black">Twitter</a></li>
            </ul>
          </div>
        </div>
        <div class="row mt-3">
          <div class="col text-center">
            <p class="mb-0">&copy; 2024 SSAFitMall. All Rights Reserved.</p>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import NavBar from "@/components/common/NavBar.vue";
import ProductCategory from "@/components/common/ProductCategoryNav.vue";
import { useUserStore } from "@/stores/userStore";
import { onMounted } from "vue";
import HomeView from "./views/HomeView.vue";
import { useProductStore } from "@/stores/product.js";

const userStore = useUserStore();
const productStore = useProductStore()

onMounted(() => {
  if (sessionStorage.getItem("access-token")) {
    const token = sessionStorage.getItem("access-token").split(".");
    const resUser = JSON.parse(atob(token[1]));
    userStore.loginUser = resUser;
  }
});
</script>
<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

header {
  flex: 1;
}

.banner {
  text-align: center; 
  height: 100%;
}

.footer {
  padding: 20px 0;
  background-color: #f5f4f4;
  font-family: 'EliceDXNeolli', sans-serif;
  color: rgb(12, 12, 12);
  margin-top: auto;
}

.footer h5 {
  font-size: 16px;
  margin-bottom: 10px;
}

.footer p, .footer li, .footer a {
  font-size: 14px;
}

.footer a {
  color: #000000;
}

.footer a:hover {
  text-decoration: underline;
}
</style>
