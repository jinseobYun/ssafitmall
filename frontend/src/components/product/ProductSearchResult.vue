<template>
  <div>
    <div v-if="store.loading">
      <LoadingSpinner />
    </div>

    <div v-if="store.productList.length === 0">
      <h2>검색된 상품이 없습니다.</h2>
    </div>
    <div v-else-if="!store.loading && paginatedProducts.length" class="main-container">
      <div class="container">
        <div v-for="product in paginatedProducts" :key="product.productCode" class="card">
          <div class="card-link" @click="loadProduct(product.productCode)">
            <div class="card-image">
              <img
                :src="`/src/assets/img/product/thumbnail/${product.productThumbnail}`"
                alt="준비중"
                class="card-img-top"
              />
              <div v-if="product.isSold === '판매중'" class="selling">판매중</div>
              <div v-if="product.isSold === '판매완료'" class="soldout">Sold Out</div>
            </div>
            <div class="card-body">
              <h3 class="card-title">{{ product.productName }}</h3>
              <p class="card-text">가격 : {{ product.productPrice }} 원</p>
            </div>
          </div>
        </div>
      </div>
      <!-- Pagination -->
      <nav aria-label="Page navigation example">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <a class="page-link" href="#" aria-label="Previous" @click.prevent="changePage(currentPage - 1)">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li
            class="page-item"
            v-for="page in totalPages"
            :key="page"
            :class="{ active: currentPage === page }"
          >
            <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: currentPage === totalPages }">
            <a class="page-link" href="#" aria-label="Next" @click.prevent="changePage(currentPage + 1)">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from "vue-router";
import { useProductStore } from "@/stores/product";
import { ref, watch, onMounted, computed } from "vue";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";

const store = useProductStore();
const router = useRouter();
const route = useRoute();
const currentPage = ref(1);
const itemsPerPage = 6;

const loadProduct = async (productCode) => {
  await store.getProduct(productCode);
  router.push(`/product/detail/${productCode}`);
};

const changePage = (page) => {
  if (page > 0 && page <= totalPages.value) {
    currentPage.value = page;
  }
};

const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return store.productList.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil(store.productList.length / itemsPerPage);
});

onMounted(() => {
  store.getProductList(route.params.category);
});

watch(
  () => route.params.category,
  (next) => {
    store.getProductList(next);
  }
);
</script>

<style scoped>

@font-face {
  font-family: 'EliceDXNeolli';
  src: url('/src/assets/fonts/EliceDXNeolli-Light.ttf') format('truetype');
  font-weight: normal;
  font-style: normal;
}
.main-container {
  font-family: 'EliceDXNeolli', sans-serif;
  margin-top: 30px;
  margin-bottom: 30px;
}

.container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.card {
  border: none;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.card:hover {
  transform: translateY(-5px);
}

.card-link {
  text-decoration: none;
  color: inherit;
}

.card-image {
  position: relative;
  width: 100%;
  height: 360px;
  overflow: hidden;
}

.card-img-top {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.selling {
  position: absolute;
  top: 10px;
  left: 10px;
  background-color: rgba(0, 0, 0, 0.6);
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
}

.soldout {
  position: absolute;
  top: 10px;
  left: 10px;
  background-color: red;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  font-weight: bold;
}

.card-body {
  padding: 15px;
}

.card-title {
  font-size: 1.2rem;
  margin-bottom: 10px;
}

.card-text {
  font-size: 1rem;
  margin-bottom: 10px;
}

.review-stats {
  display: flex;
  align-items: center;
  font-size: 0.9rem;
}

.pagination {
  margin-top: 50px;
  justify-content: center;
  color: rgb(36, 208, 255);
}

.pagination .page-link {
  color: rgb(36, 208, 255) !important;
}

.pagination .page-item.active .page-link {
  background-color: rgb(36, 208, 255) !important;
  border-color: rgb(36, 208, 255) !important;
  color: white !important;
}

.pagination .page-item.disabled .page-link {
  color: gray !important;
}


</style>
