<script setup>


import {onMounted} from "vue";
import {usePaymentStore} from "@/stores/paymentStore.js";
import {useRoute, useRouter} from "vue-router";

const route = useRoute()
const router = useRouter()
const paymentStore = usePaymentStore()

const close = async () =>{
  window.close()

}
onMounted( () => {
  close().then(()=>{
    console.log('??')
  sessionStorage.setItem("pgToken",route.query['pg-token'])
  paymentStore.moveBrowser()
  })


})
</script>

<template>

</template>

<style scoped>

</style>