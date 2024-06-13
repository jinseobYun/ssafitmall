import { defineStore } from 'pinia';
import { ref } from 'vue'; // ref를 사용하여 상태를 정의하기 위해 가져옴
import axios from 'axios'; // HTTP 요청을 보내기 위한 라이브러리

export const useChatStore = defineStore('chat', () => {
    // 상태 정의
    const messages = ref([]); // 채팅 메시지 배열
    const userInput = ref(''); // 사용자가 입력한 메시지
    const loading = ref(false); // 메시지 전송 중인지 여부
    const productName = ref(''); // 제품 이름
    const productType = ref(''); // 제품 유형

    const sendMessage = async () => {
        if (!userInput.value) return;

        const userMessage = userInput.value;
        addMessage({ type: 'user', text: userMessage });
        userInput.value = '';
        const prompt = createPrompt(userMessage);
        addMessage({ type: 'bot', text: '답변 준비중...⌛' });
        loading.value = true;

        try {
            const response = await axios.post(
                'https://api.openai.com/v1/chat/completions', // 엔드포인트 수정
                {
                    messages: [
                        { role: 'system', content: 'You are a helpful assistant. A user is asking about a product.' },
                        { role: 'user', content: `Product Name: ${productName.value}` },
                        { role: 'user', content: `Product Type: ${productType.value}` },
                        { role: 'user', content: `User Question: ${userMessage}` },
                        { role: 'assistant', content: 'Provide a detailed response:' },
                    ],
                    max_tokens: 2048,
                    temperature: 0.7,
                    model:"gpt-4o"
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${import.meta.env.VITE_APP_OPENAI_API_KEY}`,
                    },
                }
            );

            updateLastMessage(response.data.choices[0].message.content.trim());
        } catch (error) {
            updateLastMessage('🚨예기치 못한 문제가 발생했습니다. 다시 시도해주세요.');
            console.error(error);
        } finally {
            loading.value = false;
        }
    };

    const addMessage = (message) => {
        messages.value.push(message); // 메시지를 메시지 배열에 추가
    };

    const updateLastMessage = (text) => {
        if (messages.value.length > 0) {
            messages.value[messages.value.length - 1].text = text; // 메시지 배열의 마지막 메시지를 업데이트
        }
    };

    const createPrompt = (userMessage) => {
        let prompt = `You are a helpful assistant. A user is asking about a product.\n\n`;
        prompt += `Product Name: ${productName.value}\n`;
        prompt += `Product Type: ${productType.value}\n`;
        prompt += `User Question: ${userMessage}\n\n`;
        prompt += `Provide a detailed response:`;

        return prompt; // 프롬프트를 반환
    };

    const resetMessages = () => {
        messages.value = [];
    };

    return {
        messages,
        userInput,
        loading,
        productName,
        productType,
        sendMessage,
        addMessage,
        updateLastMessage,
        createPrompt,
        resetMessages
    };
});
