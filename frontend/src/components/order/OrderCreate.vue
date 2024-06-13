<template>
  <div class="order-create">
    <form @submit.prevent="writeOrder">
      <h3><strong>주문 하기</strong></h3>
      <label for="name">받는사람 : </label>
      <input type="text" id="name" v-model="orderInfo.receiverName" />
      <label for="phone">연락처 : </label>
      <input type="text" id="phone" v-model="orderInfo.receiverPhone" />
      <label for="address">주소 : </label>
      <input type="hidden" id="userId" v-model="orderInfo.userId" />
      <input type="text" id="postcode" placeholder="우편번호" v-model="zoneCode" />
      <input type="button" @click="daumPostcode" value="우편번호 찾기"><br>
      <input type="text" id="roadAddress" placeholder="도로명주소" v-model="roadAddr">
      <input type="text" id="jibunAddress" placeholder="지번주소" v-model="jibunAddr">
      <span v-if="expAddr != ''">{{ expAddr }}</span>
      <input type="text" id="detailAddress" placeholder="상세주소" v-model="detailAddr">
      <input type="text" id="extraAddress" placeholder="참고항목" v-model="extraRoadAddr">
      <button class="btn">주문 하기</button>
    </form>
  </div>
</template>

<script setup>
import { useOrderStore } from "@/stores/order";

import {onMounted, ref} from "vue";

import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore.js";
import AddressSearch from "@/components/order/AddressSearch.vue";

const store = useOrderStore();
const userStore = useUserStore();
const route = useRoute();
const router = useRouter();
const orderInfo = ref({
  receiverName: "",
  receiverPhone: "",
  recipientAddress: null,
  userId: userStore.loginUser?userStore.loginUser.userId:null,
});
// 도로명 주소
const roadAddr = ref('')
// 주소 참고 부분
const extraRoadAddr = ref("")
// 우편 번호
const zoneCode = ref("")
// 지번 주소
const jibunAddr = ref("")

// 상세 주소
const detailAddr = ref("")
// 예상 주소
const expAddr = ref("")

const writeOrder = () => {
  orderInfo.value.recipientAddress =  `${roadAddr.value}|${extraRoadAddr.value}|${zoneCode.value}|${jibunAddr.value}|${detailAddr.value}`
  console.log(orderInfo.value)
  if(orderInfo.value.receiverName === "" || orderInfo.value.receiverPhone === "" || orderInfo.value.recipientAddress === "" ||!orderInfo.value.userId  )  {
    alert('필수 항목을 입력 해주세요.')
    router.replace(0)
  }else{
    console.log(orderInfo.value)
    store.createOrder(orderInfo.value, route.params.productCode);
  }
};
onMounted(()=> {
  if(!userStore.loginUser)  {
    alert('로그인 후 이용 해주세요')
    router.push('/login')
  }
})


const daumPostcode = async () => {
  await new daum.Postcode({
    oncomplete: (data) => {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      roadAddr.value = data.roadAddress; // 도로명 주소 변수
      var extraRoadAddrSub = ''
      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
      if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
        extraRoadAddrSub += data.bname;
      }
      // 건물명이 있고, 공동주택일 경우 추가한다.
      if (data.buildingName !== '' && data.apartment === 'Y') {
        extraRoadAddrSub += (extraRoadAddrSub !== '' ? ', ' + data.buildingName : data.buildingName);
      }
      // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
      if (extraRoadAddrSub !== '') {
        extraRoadAddrSub = ' (' + extraRoadAddrSub + ')';
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      zoneCode.value = data.zonecode;
      jibunAddr.value = data.jibunAddress;

      // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
      if (roadAddr.value !== '') {
        extraRoadAddr.value = extraRoadAddrSub;
      }

      // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
      if (data.autoRoadAddress) {
        expAddr.value = '(예상 도로명 주소 : ' + data.autoRoadAddress + ')';
        expAddr.value += data.autoRoadAddress + extraRoadAddr.value;
      } else if (data.autoJibunAddress) {
        expAddr.value = '(예상 지번 주소 : ' + data.autoJibunAddress + ')';
      } else {
        expAddr.value = '';
      }
    }

  }).open();
}
</script>
<style scoped>
h3 {
  text-align: center;
  margin-top: 30px;
  margin-bottom: 30px;
}
.order-create {
  max-width: 600px;
  margin: 50px auto;  /* 페이지 상단과 더 가까워지도록 조정 */
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  font-family: 'EliceDXNeolli';
}

.order-create label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
}

.order-create input[type="text"],
.order-create input[type="hidden"],
.order-create input[type="button"],
.order-create input[type="password"] {
  width: 100%;
  padding: 8px;
  margin-bottom: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.order-create input[type="button"] {
  background-color: rgb(93, 142, 156);
  color: white;
  cursor: pointer;
}

.order-create .btn {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: rgb(46, 46, 46);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 20px;  /* 간격 조정 */
}
</style>
