<template>
  <div class="product-detail-container">
    <div v-if="store.loading">
      <LoadingSpinner />
    </div>
    <div v-if="!store.loading && store.product" class="product-detail">
      <div class="product-image">
        <img
          :src="`/src/assets/img/product/thumbnail/${store.product.productThumbnail}`"
          alt="상품"
          class="main-image"
        />
      </div>
      <div class="product-info">
        <h4 class="product-name">{{ store.product.productName }}</h4>
        <p class="product-price">{{ store.product.productPrice }}원</p>
        <RouterLink :to="`/order/${productCode}`" class="btn btn-primary">구매하기</RouterLink>
        <button v-if="isLike" @click="removeCart()" class="btn like-button disable">💖</button>
        <button v-else @click="insertCart()" class="btn like-button enable">🤍</button>
        <div class="seller-info">판매자 : {{ store.product.userId }}</div>
        <div class="product-description">{{ store.product.productDesc }}</div>
        <!-- 캐릭터 이미지 버튼 -->
        <img @click="openModal" src="/src/assets/img/character.png" alt="챗봇 캐릭터" class="chatbot-character"/>
      </div>
    </div>
    <div class="actions">
      <button @click="back" class="btn btn-secondary">목록으로</button>
      <div v-if="userStore.loginUser && store.product && store.product.userId === userStore.loginUser.userId">
        <button @click="updateProduct" class="btn btn-secondary">상품 수정</button>
        <button @click="deleteProduct" class="btn btn-danger">상품 삭제</button>
      </div>
    </div>

    <!-- 모달 창 -->
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <ChatGptApi :product-name="store.product.productName" :product-type="store.product.categoryCode"/>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useProductStore } from "@/stores/product";
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted, ref } from "vue";
import ChatGptApi from "../common/ChatGptApi.vue";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import { useUserStore } from "@/stores/userStore.js";

const store = useProductStore();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const productCode = computed(() => Number(route.params.productCode));
const isLike = ref(false)
const insertCart = () => {
  if(!userStore.loginUser) {
    alert('로그인 후 이용 해주세요')
    router.push("/login");
  }else{
  store.addCart(productCode.value);
  isLike.value = true;
  }
};

const removeCart = () => {
  if(!userStore.loginUser) {
    alert('로그인 후 이용 해주세요')
    router.push("/login");
  }
  else{
  store.deleteCart({ userId: userStore.loginUser.userId, productCode: productCode.value });
  isLike.value = false;
  }
};

const updateProduct = () => {
  if (!store.product) {
    store.getProduct(productCode.value);
  }
  router.push(`/product/update/${productCode.value}`);
};

const deleteProduct = () => {
  store.deleteProduct(productCode.value);
};
console.log(isLike.value)
onMounted(async () => {
    await store.getProduct(productCode.value);
  if (userStore.loginUser) {
    await store.selectCartList(userStore.loginUser.userId).then((res)=>{
      console.log(res)
      res.some(cartItem => {console.log(cartItem.productCode === productCode.value)
      console.log(`${cartItem.productCode}  === ${productCode.value}`)
      console.log(typeof cartItem.productCode)
      console.log(typeof productCode.value)
      ;})
    isLike.value =  res.some(cartItem => cartItem.productCode === productCode.value);
    }).then(()=>console.log(isLike.value))

  }
});

const back = () => {
  router.replace(`/product/${store.product.categoryCode}`);
};

// 모달 창 열기/닫기 상태 관리
const showModal = ref(false);
const openModal = () => {
  showModal.value = true;
};
const closeModal = () => {
  showModal.value = false;
};
</script>
<style scoped>
.product-detail-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  font-family: 'EliceDXNeolli', sans-serif;
}

.product-detail {
  display: flex;
  width: 80%;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  padding: 20px;
  gap: 20px; /* 이미지와 정보 간격 조정 */
}

.product-image, .product-info {
  flex: 1 1 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.main-image {
  width: 100%;
  max-width: 560px;
  height: auto;
  object-fit: cover;
  border: 1px solid #ddd;
}

.product-info {
  margin-top: 100px;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  position: relative;
}

.product-name {
  font-size: 24px;
  color: #000;
  margin-bottom: 10px;
}

.product-price {
  font-size: 18px;
  color: #555;
  margin-bottom: 20px;
}

.product-description {
  margin-top: 20px;
}

.seller-info {
  margin-top: 20px;
  font-size: 16px;
  color: #777;
}

.actions {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.btn {
  margin: 5px;
}

.like-button {
  font-size: 20px;
  border: none;
  background-color: transparent;
  cursor: pointer;
}

.enable {
  color: black;
}

.disable {
  color: red;
  font-size: 30px;
}

.chatbot-character {
  width: 50px;
  height: 50px;
  position: absolute;
  bottom: 0;
  right: 0;
  cursor: pointer;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 800px;
  border-radius: 10px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
