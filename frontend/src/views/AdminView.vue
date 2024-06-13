<script setup>

</script>

<template>
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" aria-current="page" href="#">
        <RouterLink to="/adminpage/userList">유저 목록</RouterLink>
      </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#"><RouterLink to="/adminpage/productList">상품 목록</RouterLink></a>
      </li>
  </ul>
<RouterView/>
</template>

<style scoped>
.nav-tabs{
  margin-top: 30px;
  margin-left: 10px;
}
</style>