<template>
  <div>
    <RouterView />
  </div>
</template>

<script setup>
import ProductList from "@/components/product/ProductList.vue";
import { useRoute, useRouter } from "vue-router";
import { watch, onMounted } from "vue";
import { useProductStore } from "@/stores/product";

const store = useProductStore();
const route = useRoute();
const router = useRouter();
onMounted(() => {
  if (route.fullPath === '/product') {

    router.push("/product/ALL");
  }
});
</script>

<style scoped></style>
