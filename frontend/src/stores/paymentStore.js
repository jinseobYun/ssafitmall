import { defineStore } from 'pinia';
import axios from 'axios';
import { ref } from 'vue';
import {useUserStore} from "@/stores/userStore.js";
import {useRoute, useRouter} from "vue-router";
import {useOrderStore} from "@/stores/order.js";

const ORDER_API_URL = "http://localhost:8080/order/";
const PAYMENT_API_URL = "http://localhost:8080/payment";
export const usePaymentStore = defineStore('payment', () => {
    const nextRedirectUrl = ref('');
    const router = useRouter();
    const route = useRoute();
    const tidInfo = ref({})
    const payment = ref({})
    const orderStore = useOrderStore();
    const preparePayment = async (order,product) => {
        console.log(order)
        console.log(product)
        sessionStorage.setItem("product",JSON.stringify(product))
        sessionStorage.setItem("order",JSON.stringify(order))
        try {
            const response = await axios.post('/api/online/v1/payment/ready', {
                cid: 'TC0ONETIME', // 테스트용 CID
                partner_order_id: order.orderNumber,
                partner_user_id: order.userId,
                item_name: product.productName,
                quantity: 1,
                total_amount: product.productPrice,
                vat_amount: 200,
                tax_free_amount: 0,
                approval_url: `http://localhost:5173/paymentProccesing/${order.orderNumber}`,
                cancel_url: `http://localhost:5173/order/orderDetail/${order.orderNumber}`,
                fail_url: `http://localhost:5173/order/orderDetail/${order.orderNumber}`
            }, {
                headers: {
                    'Authorization':`SECRET_KEY ${import.meta.env.VITE_APP_KAKAO_PAY_API_KEY}`,
                    'Content-Type': 'application/json'
                }
            });

            sessionStorage.setItem("tid",JSON.stringify(response.data.tid))
            payment.value = {
                partnerOrderId: order.orderNumber,
                partnerUserId: order.userId,
                itemName: product.productName,
                quantity: 1,
                totalAmount: product.productPrice,
                vatAmount: 200,
                taxFreeAmount: 0,
                productCode:product.productCode,
                tid:response.data.tid
            }
            sessionStorage.setItem("payment",JSON.stringify(payment.value))
            console.log(payment.value)
            nextRedirectUrl.value = response.data.next_redirect_pc_url;
            window.location.href = nextRedirectUrl.value
            return response.data
        } catch (error) {
            console.error('Payment preparation failed:', error);
        }
    };

    const approvePayment = (pgToken) => {
        const orderInfo = JSON.parse(sessionStorage.getItem("order"));
        const tidInfo = JSON.parse(sessionStorage.getItem("tid"));
        payment.value = JSON.parse(sessionStorage.getItem("payment"));
        const data = {
            'cid': 'TC0ONETIME',
            'tid': tidInfo,
            'partner_order_id': orderInfo.orderNumber,
            'partner_user_id': orderInfo.userId,
            'pg_token': pgToken
        };

        axios.post('/api/online/v1/payment/approve',data,{
            headers: {
                'Authorization': `SECRET_KEY ${import.meta.env.VITE_APP_KAKAO_PAY_API_KEY}`,
                    'Content-Type': 'application/json'
            }
        })
        .then(response => {
            console.log(response)
            // 결제 성공일 경우
            if (response.statusText==='OK') {
                orderInfo.deliveryStatus = '결제완료'
                console.log(payment.value)

                axios.post(`${PAYMENT_API_URL}`,payment.value).then(()=>{
                    axios.put(`${ORDER_API_URL}status/${orderInfo.orderNumber}`,orderInfo).then((res) => {
                        router.push(`/paymentSuccess/${orderInfo.orderNumber}`); // 결제 성공 페이지로 이동
                    }).catch((err)=>{
                        console.log("주문 결제 상태 수정중 에러");
                        console.log(err);
                        router.push('/paymentFail');
                    })
                }).catch((err)=>{
                    console.log("주문 결제 정보 insert 에러");
                    console.log(err)
                    router.push('/paymentFail');
                })

            } else {
                router.push('/paymentFail'); // 결제 실패 페이지로 이동
            }
        })
        .catch(error => {
            console.error('Failed to process payment:', error);
            router.push('/paymentFail'); // 결제 실패 페이지로 이동
        });
    }

    const cancelPayment = async (productCode,orderInfo) => {

        const paymentInfo = await axios.get(`${PAYMENT_API_URL}/${productCode}`).then((res) => {return res.data});

         await axios.post('/api/online/v1/payment/cancel', {
            cid: 'TC0ONETIME', // 테스트용 CID
             "tid": paymentInfo.tid,
             "cancel_amount": paymentInfo.totalAmount,
             "cancel_tax_free_amount": paymentInfo.taxFreeAmount,
             "cancel_vat_amount": paymentInfo.vatAmount,
             "cancel_available_amount": paymentInfo.totalAmount,
        }, {
            headers: {
                'Authorization':`SECRET_KEY ${import.meta.env.VITE_APP_KAKAO_PAY_API_KEY}`,
                'Content-Type': 'application/json'
            }
        }).then((res)=>{

            if(res.statusText==='OK') {

                orderInfo.deliveryStatus = '결제대기'
                axios.delete(`${PAYMENT_API_URL}/${productCode}`)
               .then(()=>{
                    axios.put(`${ORDER_API_URL}status/${orderInfo.orderNumber}`,orderInfo).then((res) => {
                        alert('결제가 취소 되었습니다.')
                        orderStore.getOrderInfo(orderInfo.orderNumber)
                    }).catch((err)=>{
                        console.log("주문 결제 상태 수정중 에러");
                        console.log(err);
                        router.push('/paymentFail');
                    })

                })
                    .catch((err)=>{
                    console.log('주문 취소 오류')
                    console.log(err)
                })
            }
            else{
                alert('결제 취소 오류')
            }
         })





    }

    return { nextRedirectUrl, preparePayment,approvePayment,cancelPayment };
});
