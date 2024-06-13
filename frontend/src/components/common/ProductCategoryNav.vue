<template>
  <div class="sub-nav">
    <RouterLink to="/product/ORG"> 유기농 </RouterLink>
    <RouterLink to="/product/GAI"> 게이너 </RouterLink>
    <RouterLink to="/product/PRO"> 프로틴 </RouterLink>
    <RouterLink to="/product/AMI"> 아미노산 </RouterLink>
    <RouterLink to="/product/DIET"> 다이어트 </RouterLink>
    <RouterLink to="/product/ENE"> 에너지 </RouterLink>
    <RouterLink to="/product/NUT"> 영양제 </RouterLink>
    <RouterLink to="/product/SHA"> 쉐이커 </RouterLink>
    <RouterLink to="/product/HEA"> 헬스용품 </RouterLink>
  </div>
</template>

<script setup></script>

<style scoped>
.sub-nav {
  margin: 0px 20px;
  padding: 10px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-top: 1px solid rgb(230, 230, 230);
  border-bottom: 1px solid rgb(230, 230, 230);
  font-family: 'EliceDXNeolli', sans-serif;
  height: 50px;
}

.sub-nav a {
  text-decoration: none;
  font-weight: bold;
  color: black;
  font-size: 17px;
  display: inline-block;
  margin: 0;
  text-transform: uppercase;
}
.sub-nav a::after {
  display: block;
  content: "";
  border-bottom: solid 4px rgb(36, 208, 255);
  transform: scaleX(0);
  transition: transform 250ms ease-in-out;
}
.sub-nav a:hover {
  color: rgb(36, 208, 255);
}

.sub-nav a:hover:after {
  transform: scaleX(1);
}
.sub-nav a.fromRight:after {
  transform-origin: 100% 50%;
}
.sub-nav a.fromLeft:after {
  transform-origin: 0% 50%;
}
</style>
