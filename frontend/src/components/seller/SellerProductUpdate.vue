<template>
  <div v-if="product" class="product-update-container">
    <h2>상품을 수정해주세요.</h2>
    <div class="mb-3">
      <label for="thumbnail" class="form-label">상품 썸네일</label>
      <input class="form-control" type="file" id="thumbnail" accept="image/*" @change="thumbnailUpload">
    </div>
    <div class="mb-3">
      <label for="pName" class="form-label">상품명</label>
      <input class="form-control" id="pName" type="text" v-model="product.productName" />
    </div>
    <div class="mb-3">
      <label for="price" class="form-label">가격</label>
      <input class="form-control" id="price" type="number" v-model="product.productPrice" />
    </div>
    <div class="mb-3">
      <label for="desc" class="form-label">상품 설명</label>
      <textarea class="form-control" id="desc" rows="3" v-model="product.productDesc"></textarea>
    </div>
    <!-- 상품 이미지 업로드  -->
    <div class="mb-3">
      <label for="imgs" class="form-label">상품 이미지 등록</label>
      <input class="form-control" type="file" id="imgs" accept="image/*" @change="imgUpload" multiple>
      <div v-if="imgs.length" class="mt-3">
        <p>선택된 이미지</p>
        <ul class="list-group">
          <li v-for="(file, index) in imgs" :key="index" class="list-group-item d-flex justify-content-between align-items-center">
            {{ file.name }}
            <button class="btn btn-sm btn-outline-danger" @click="deleteImg(index)">삭제</button>
          </li>
        </ul>
      </div>
    </div>

    <input type="hidden" :value="product.userId" />
    <div class="mb-3">
      <label class="form-label">카테고리</label>
      <div class="d-flex flex-wrap">
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option1" value="ORG" v-model="product.categoryCode">
          <label class="form-check-label" for="option1">유기농</label>
        </div>
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option2" value="GAI" v-model="product.categoryCode">
          <label class="form-check-label" for="option2">게이너</label>
        </div>
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option3" value="PRO" v-model="product.categoryCode">
          <label class="form-check-label" for="option3">프로틴</label>
        </div>
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option4" value="ENE" v-model="product.categoryCode">
          <label class="form-check-label" for="option4">에너지</label>
        </div>
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option5" value="AMI" v-model="product.categoryCode">
          <label class="form-check-label" for="option5">아미노산</label>
        </div>
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option6" value="DIET" v-model="product.categoryCode">
          <label class="form-check-label" for="option6">다이어트</label>
        </div>
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option7" value="NUT" v-model="product.categoryCode">
          <label class="form-check-label" for="option7">영양제</label>
        </div>
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option8" value="SHA" v-model="product.categoryCode">
          <label class="form-check-label" for="option8">쉐이커</label>
        </div>
        <div class="form-check me-3">
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="option9" value="HEA" v-model="product.categoryCode">
          <label class="form-check-label" for="option9">헬스용품</label>
        </div>
      </div>
    </div>
    <button type="button" class="btn btn-outline-dark mt-3" @click="updateProduct">수정</button>
  </div>
</template>

<script setup>
import { useSellerStore } from "@/stores/sellerStore.js"
import { useUserStore } from '@/stores/userStore';
import { useRoute, useRouter } from "vue-router";

import { ref, onMounted } from "vue";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const sellerStore = useSellerStore();


const props = defineProps({
  productCode : Number
});

const emits = defineEmits(['update'])

const product = ref();

const imgs = ref([]);
const thumbnail = ref(null);


const thumbnailUpload = (e) => {
  thumbnail.value = e.target.files[0];
};

const imgUpload = (e) => {
  const selectedFiles = Array.from(e.target.files);
  imgs.value.push(...selectedFiles);
};

const updateProduct = () => {
  console.log()
  emits('update',{product,thumbnail,imgs})
}

const deleteImg = (idx) => {
  imgs.value.splice(idx, 1);
};


onMounted(async ()=>{
  product.value = await sellerStore.getProduct(props.productCode);
})
</script>

<style scoped>
.product-update-container {
  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  font-family: 'EliceDXNeolli', sans-serif;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-label {
  font-weight: bold;
  
}

.form-check {
  margin-bottom: 10px;
}

.list-group-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border: 1px solid #ddd;
  margin-bottom: 5px;
}

.btn-outline-dark {
  display: block;
  width: 100%;
  margin-top: 50px;
}

</style>
