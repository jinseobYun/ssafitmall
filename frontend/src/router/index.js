import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import ProductView from "../views/ProductView.vue";
import OrderView from "../views/OrderView.vue";
import ProductDetail from "@/components/product/ProductDetail.vue";
import ProductList from "@/components/product/ProductList.vue";
import OrderInfo from "@/components/order/OrderInfo.vue";
import OrderCreate from "@/components/order/OrderCreate.vue";
import AfterOrder from "@/components/order/AfterOrder.vue";
import OrderDetail from "@/components/order/OrderDetail.vue";
import Login from "@/components/user/Login.vue";
import Regist from "@/components/user/Regist.vue";
import MyPage from "@/components/user/MyPage.vue";
import SellerPage from "@/components/seller/SellerPage.vue";
import SellerProductCreate from "@/components/seller/SellerProductCreate.vue";
import SellerProductUpdate from "@/components/seller/SellerProductUpdate.vue";
import userCart from "@/components/user/userCart.vue";
import AdminView from "@/views/AdminView.vue";
import AdminUserList from "@/components/admin/AdminUserList.vue";
import AdminUserDetail from "@/components/admin/AdminUserDetail.vue";
import AdminProductList from "@/components/admin/AdminProductList.vue";
import ProductSearchResult from "@/components/product/ProductSearchResult.vue";
import OrderUpdate from "@/components/order/OrderUpdate.vue";
import ProductUpdate from "@/components/product/ProductUpdate.vue";
import FindUserId from "@/components/user/FineUserId.vue";
import FindUserPwd from "@/components/user/FineUserPwd.vue"
import LoginKakao from "@/components/user/LoginKakao.vue";
import AfterPayment from "@/components/payment/AfterPayment.vue";
import PaymentProcessing from "@/components/payment/PaymentProcessing.vue";
import PaymentSuccess from "@/components/payment/PaymentSuccess.vue";
import PaymentFail from "@/components/payment/PaymentFail.vue";
import KakaoPaymentReady from "@/components/payment/KakaoPaymentReady.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/product",
      name: "product",
      component: ProductView,
      children: [
        {
          path: ":category",
          name: "productList",
          component: ProductList,
        },
        {
          path: "detail/:productCode",
          name: "productDetail",
          component: ProductDetail,
        },
        {
          path: "update/:productCode",
          name: "update",
          component: ProductUpdate,
        },
        {
          path: "searchResult",
          name: "searchResult",
          component: ProductSearchResult,
        }
      ],
    },
    {
      path: "/order",
      name: "order",
      component: OrderView,
      children: [
        {
          path: ":productCode",
          name: "orderInfo",
          component: OrderInfo,
        },
        {
          path: "generate/:productCode",
          name: "orderGenerate",
          component: OrderCreate,
        },
        {
          path: "successOrder/:orderNumber",
          name: "successOrder",
          component: AfterOrder,
        },
        {
          path: "orderDetail/:orderNumber",
          name: "orderDetail",
          component: OrderDetail,
        },
        {
          path: 'orderUpdate/:orderNumber',
          name: "orderUpdate",
          component: OrderUpdate,
        },
      ],
    },
    {
      path: "/login",
      name: "login",
      component: Login,
    },
    {
      path: "/regist",
      name: "regist",
      component: Regist,
    },
    {
      path:"/finduserid",
      name:"findUserId",
      component:FindUserId,
    },
    {
      path:"/finduserpwd",
      name:"findUserPwd",
      component:FindUserPwd,
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MyPage,
    },
    {
      path: "/adminpage",
      name: "adminpage",
      component: AdminView,
      children:[
        {
          path: 'userList',
          name: "adminUserList",
          component: AdminUserList,
        },
        {
          path: 'userDetail/:userId',
          name: "adminUserDetail",
          component: AdminUserDetail,

        },
        {
          path: 'productList',
          name: "adminProductList",
          component: AdminProductList,
        },

      ]
    },
    {
      path: "/sellerpage",
      name: "sellerpage",
      component: SellerPage,
      children: [
        {
          path: "create",
          name: "sellerProductCreate",
          component: SellerProductCreate,
        },
        {
          path: "update/:productCode",
          name: "sellerProductUpdate",
          component: SellerProductUpdate,
        },
      ]
    },
    {
      path: "/cart",
      name: "cart",
      component: userCart,
    },
    
    {
      path: '/user/login/kakao',
      name: 'LoginKakao',
      component: LoginKakao
    },

    {
      path:'/paymentProccesing/:orderNumber',
      name: "paymentProccesing",
      component: PaymentProcessing,
    },
    {
      path:'/paymentSuccess/:orderNumber',
      name: "paymentSuccess",
      component: PaymentSuccess,
    },
    {
      path:'/paymentFail',
      name: "paymentFail",
      component: PaymentFail,
    },
    {
      path:'/paymentReady',
      name:'paymentReady',
      component: KakaoPaymentReady
    }
  ],
});

export default router;
