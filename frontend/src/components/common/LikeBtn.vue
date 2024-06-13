<script setup>
import {useProductStore} from "@/stores/product.js";
import {computed, onMounted, ref} from "vue";
import {useUserStore} from "@/stores/userStore.js";

const productStore = useProductStore();
const userStore = useUserStore();
const props = defineProps({
   productCode:Number
 })

onMounted(()=>{
})
</script>

<template>

</template>

<style scoped>
</style>