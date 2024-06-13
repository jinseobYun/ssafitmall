<template>
  <div>
    <h1>Payment Processing</h1>
    <p>{{ message }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute,useRouter } from 'vue-router';
import axios from 'axios';
import {usePaymentStore} from "@/stores/paymentStore.js";

const paymentStore = usePaymentStore()
const route = useRoute()
const router = useRouter();
const message = ref('');

let index = 0;
const messages = ['결', '제', ' ', '진', '행', '중', ' ', '입', '니', '다'];

const showNextLetter = () => {
  if (index < messages.length) {
    message.value += messages[index];
    index++;
    setTimeout(showNextLetter, 100);
  } else {
    index = 0;
    message.value = '';
    setTimeout(showNextLetter, 100);
  }
};

onMounted(() => {
  showNextLetter();
  // 결제 승인 요청 보내기
  console.log(route)
  console.log(route.query.pg_token)
  paymentStore.approvePayment(route.query.pg_token)

});

</script>

<style scoped>
</style>