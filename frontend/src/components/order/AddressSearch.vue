<template>
  <div class="address-search">
    <input type="text" id="postcode" placeholder="우편번호" v-model="zoneCode" />
    <input type="button" @click="daumPostcode" value="우편번호 찾기" class="btn"><br>
    <input type="text" id="roadAddress" placeholder="도로명주소" v-model="roadAddr">
    <input type="text" id="jibunAddress" placeholder="지번주소" v-model="jibunAddr">
    <span v-if="expAddr">{{ expAddr }}</span>
    <input type="text" id="detailAddress" placeholder="상세주소" v-model="detailAddr">
    <input type="text" id="extraAddress" placeholder="참고항목" v-model="extraRoadAddr">
  </div>
</template>

<script setup>
import { ref } from 'vue';

const roadAddr = ref('');
const extraRoadAddr = ref('');
const zoneCode = ref('');
const jibunAddr = ref('');
const detailAddr = ref('');
const expAddr = ref('');

const addrs = ref({
  roadAddr,
  extraRoadAddr,
  zoneCode,
  jibunAddr,
  detailAddr
});

const emits = defineEmits(['complete']);

const daumPostcode = () => {
  new daum.Postcode({
    oncomplete: (data) => {
      roadAddr.value = data.roadAddress;
      let extraRoadAddrSub = '';
      if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
        extraRoadAddrSub += data.bname;
      }
      if (data.buildingName !== '' && data.apartment === 'Y') {
        extraRoadAddrSub += (extraRoadAddrSub !== '' ? ', ' + data.buildingName : data.buildingName);
      }
      if (extraRoadAddrSub !== '') {
        extraRoadAddrSub = ' (' + extraRoadAddrSub + ')';
      }
      zoneCode.value = data.zonecode;
      jibunAddr.value = data.jibunAddress;
      if (roadAddr.value !== '') {
        extraRoadAddr.value = extraRoadAddrSub;
      }
      if (data.autoRoadAddress) {
        expAddr.value = '(예상 도로명 주소 : ' + data.autoRoadAddress + ')';
        expAddr.value += data.autoRoadAddress + extraRoadAddr.value;
      } else if (data.autoJibunAddress) {
        expAddr.value = '(예상 지번 주소 : ' + data.autoJibunAddress + ')';
      } else {
        expAddr.value = '';
      }
      emits('complete', { 'addrs': addrs.value });
    }
  }).open();
};
</script>

<style scoped>
.address-search {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.address-search input[type="text"] {
  width: calc(100% - 20px);
  padding: 8px;
  margin-bottom: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.address-search .btn {
  padding: 8px 16px;
  background-color: #009879;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
}

.address-search .btn:hover {
  background-color: #007f5f;
}
</style>
