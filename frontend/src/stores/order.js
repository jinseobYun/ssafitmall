import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useRouter } from "vue-router";
import { useProductStore } from "./product";

const ORDER_API_URL = "http://localhost:8080/order/";

const PRODUCT_API_URL = "http://localhost:8080/product/";

export const useOrderStore = defineStore("order", () => {
  const router = useRouter();
  const productStore = useProductStore();
const loading = ref(false);

  const createOrder = (order, productCode) => {
    order = {...order,productCode};
    console.log(order);
    axios.post(`${ORDER_API_URL}${productCode}`, order).then((res) => {
      router.push(`/order/successOrder/${res.data}`);
    }).catch((err)=>{
      console.log("주문 등록에서 오류");
      console.log(err);
    });
  };

  const orderInfo = ref(null);

  const getOrderInfo = async (orderNumber) => {
    loading.value = true;
    await axios
      .get(`${ORDER_API_URL}${orderNumber}`)
      .then((res) => {
           orderInfo.value = res.data;
        if (
          !productStore.product || productStore.product.productCode !== res.data.productCode
        ) {
          axios.get(`${PRODUCT_API_URL}${res.data.productCode}`).then((res) => {
            productStore.product = res.data;
          });
        }
      }).catch((e)=>
    {
      console.log('getOrderInfo Err')
      console.log(e)
      alert('주문 정보를 불러오는데 실패했습니다.')
      loading.value = false;
      router.push('/')
    }).finally(()=>{
      loading.value = false;
        })


  };
    const updateOrder = () => {
      axios.put(ORDER_API_URL,orderInfo.value).then(()=>{
        router.push(`/order/orderDetail/${orderInfo.value.orderNumber}`);
      }).catch((e)=>{
        console.log('updateOrder Err')
        console.log(e);
        alert('주문 정보 수정중 오류가 발생했습니다.')
        router.push(`/order/orderDetail/${orderInfo.value.orderNumber}`)
      })
    }

    const deleteOrder = (orderNumber) => {
      axios.delete(`${ORDER_API_URL}${orderNumber}`).then((res) => {
        router.push(`/`);
      }).catch((e)=>{
        console.log('deleteOrder Err')
        console.log(e)
        alert('주문 정보를 삭제하는 중 오류가 발생했습니다.')
        router.push('/')
      })
    }

  return { createOrder, orderInfo, getOrderInfo ,updateOrder,loading,deleteOrder};
});
