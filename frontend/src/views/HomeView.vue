<template>
  <div>
    <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
      </div>
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="/src/assets/img/banner/배너3.png" class="d-block w-100" alt="...">
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
    <div class="best-products-section">
      <div class="section-header">
        <div class="best-label"><strong>BEST | </strong></div>
      </div>
      <div class="products-carousel-wrapper">
        <div class="products-carousel" ref="bestCarousel" :style="{ transform: `translateX(-${currentBestSlide * 100}%)` }">
          <div v-for="(product, index) in likeProduct" :key="index" class="product-item">
            <a class="card-link" @click.prevent="loadProduct(product.productCode)">
              <div class="card-image">
                <img :src="`/src/assets/img/product/thumbnail/${product.productThumbnail}`" alt="Product Image" class="card-img-top">
              </div>
              <div class="card-body">
                <h3 class="card-title">{{ product.productName }}</h3>
                <p class="card-text">가격 : {{ product.productPrice }} 원</p>
              </div>
            </a>
          </div>
        </div>
        <button class="carousel-control-prev" @click="prevBestSlide">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" @click="nextBestSlide">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </div>
    <div class="new-products-section">
      <div class="section-header">
        <div class="new-label"><strong>NEW | </strong></div>
      </div>
      <div class="products-carousel-wrapper">
        <div class="products-carousel" ref="newCarousel" :style="{ transform: `translateX(-${currentNewSlide * 100}%)` }">
          <div v-for="(product, index) in newProduct" :key="index" class="product-item">
            <a class="card-link" @click.prevent="loadProduct(product.productCode)">
              <div class="card-image">
                <img :src="`/src/assets/img/product/thumbnail/${product.productThumbnail}`" alt="Product Image" class="card-img-top">
              </div>
              <div class="card-body">
                <h3 class="card-title">{{ product.productName }}</h3>
                <p class="card-text">가격 : {{ product.productPrice }} 원</p>
              </div>
            </a>
          </div>
        </div>
        <button class="carousel-control-prev" @click="prevNewSlide">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" @click="nextNewSlide">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useProductStore } from "@/stores/product.js";
import { computed, onMounted, ref } from "vue";
import { useRouter } from "vue-router";

const productStore = useProductStore();
const router = useRouter();
const currentBestSlide = ref(0);
const currentNewSlide = ref(0);

const loadProduct = async (productCode) => {
  await productStore.getProduct(productCode);
  router.push(`/product/detail/${productCode}`);
};

const likeProduct = computed(() => {

  return productStore.likeCntOrderProductList;

});

const newProduct = computed(() => {
  return productStore.latestOrderProductList;
});

const prevBestSlide = () => {
  if (currentBestSlide.value > 0) {
    currentBestSlide.value--;
  }
};

const nextBestSlide = () => {
  if (currentBestSlide.value < Math.ceil(likeProduct.value.length / 3) - 1) {
    currentBestSlide.value++;
  }
};

const prevNewSlide = () => {
  if (currentNewSlide.value > 0) {
    currentNewSlide.value--;
  }
};

const nextNewSlide = () => {
  if (currentNewSlide.value < Math.ceil(newProduct.value.length / 3) - 1) {
    currentNewSlide.value++;
  }
};

onMounted(() => {
  if (productStore.likeCntOrderProductList.length === 0 || productStore.latestOrderProductList.length === 0) {
    productStore.initPage();
  }
});
</script>

<style scoped>
.carousel-inner img {
  height: 300px;
  object-fit: cover;
}

.best-products-section,
.new-products-section {
  margin: 50px 0;
  font-family: 'EliceDXNeolli', sans-serif;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 0 20px;
}

.best-label,
.new-label {
  font-size: 24px;
  font-weight: bold;
  margin-left: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.more-button {
  background: none;
  border: none;
  color: #007bff;
  font-size: 16px;
  cursor: pointer;
  text-decoration: underline;
}

.products-carousel-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  overflow: hidden;  
}

.products-carousel {
  display: flex;
  transition: transform 0.5s ease;
  width: calc(33.33% * 5);  /* 총 5개의 상품 슬라이드를 위한 너비 설정 */
}

.product-item {
  flex: 0 0 calc(100% / 3);  /* 3개의 상품이 한 화면에 보이도록 설정 */
  display: flex;
  flex-direction: column;
  align-items: center;
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

.carousel-control-prev,
.carousel-control-next {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 5%;
  background: none;
  border: none;
  cursor: pointer;
}

.carousel-control-prev {
  left: 0;
}

.carousel-control-next {
  right: 0;
}
</style>
