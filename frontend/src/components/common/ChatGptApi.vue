<template>
  <div class="chat-bot">
    <h2>새롭게 추가된 AI 기능을 활용해보세요!</h2>
    <div class="chat-window">
      <div v-for="(message, index) in chatStore.messages" :key="index" :class="['message', message.type]">
        <pre>{{ message.text }}</pre>
      </div>
    </div>
    <div class="suggested-questions">
      <transition-group name="slide-fade" tag="div">
        <button v-for="(question, index) in visibleQuestions" :key="index" @click="askQuestion(question.type)">
          {{ question.text }}
        </button>
      </transition-group>
    </div>
  </div>
</template>

<script setup>
import { useChatStore } from '@/stores/chatGpt';
import { onMounted, onUnmounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { useProductStore } from '@/stores/product.js';

const props = defineProps({
  productName: String,
  productType: String,
});

const route = useRoute();
const chatStore = useChatStore();
const productStore = useProductStore();
const visibleQuestions = ref([]);
const allQuestions = ref([]);

onMounted(async () => {
  await productStore.getProduct(route.params.productCode);
  if (!props.productName) {
    chatStore.productName = productStore.product.productName;
  } else {
    chatStore.productName = props.productName;
  }
  if (!props.productType) {
    chatStore.productType = productStore.product.categoryCode;
  } else {
    chatStore.productType = props.productType;
  }
  generateQuestions();
  showQuestions();
});

// 페이지를 떠날 때 실행되는 함수
onUnmounted(() => {
  chatStore.resetMessages();
  visibleQuestions.value = [];
  allQuestions.value = [];
});

const generateQuestions = () => {
  if (chatStore.productType === 'HEA') {
    allQuestions.value.push(
        { text: `${chatStore.productName}의 사용법이 궁금하신가요? 챗봇에게 물어보기`, type: 'usage' },
        { text: `${chatStore.productName}의 용도와 이 용품을 사용해서 할 수 있는 운동을 추천해줘`, type: 'info' }
    );
  } else {
    allQuestions.value.push(
        { text: `${chatStore.productName}의 복용법과 효능, 부작용을 알려줘`, type: 'info' }
    );
  }
  allQuestions.value.push(
      { text: `${chatStore.productName}의 가격을 비교해서 표로 보여줘`, type: 'price' }
  );
};

const showQuestions = () => {
  let index = 0;
  const interval = setInterval(() => {
    if (index < allQuestions.value.length) {
      visibleQuestions.value.push(allQuestions.value[index]);
      index++;
    } else {
      clearInterval(interval);
    }
  }, 500); // 0.5초 간격으로 질문 추가
};

const askQuestion = (type) => {
  const question = allQuestions.value.find(q => q.type === type).text;
  visibleQuestions.value = visibleQuestions.value.filter(q => q.type !== type);
  chatStore.userInput = question;
  chatStore.sendMessage();
};
</script>

<style>
.chat-bot {
  border: 1px solid #ccc;
  border-radius: 20px;
  padding: 20px;
  max-width: 800px;
  background-color: #f9f9f9;
}

.chat-window {
  max-height: 600px;
  overflow-y: auto;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 10px;
  background-color: #ffffff;
}

.message {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 10px;
  white-space: pre-wrap;
}

.message.user {
  text-align: right;
  background-color: #e0f7fa;
}

.message.bot {
  text-align: left;
  background-color: #e0f4ff;
}

.suggested-questions {
  margin-bottom: 10px;
}

.suggested-questions button {
  display: block;
  margin: 5px 0;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 10px;
  background-color: #ffeb3b;
  color: #000;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.suggested-questions button:hover {
  background-color: #ffc107;
}

.slide-fade-enter-active {
  transition: all 1s ease;
}
.slide-fade-leave-active {
  transition: all 1s ease;
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateY(30px);
  opacity: 0;
}

button[disabled] {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
