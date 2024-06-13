import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import adminProductList from "@/components/admin/AdminProductList.vue";

const ADMIN_API_URL = `http://localhost:8080/admin`;

const PRODUCT_API_URL = "http://localhost:8080/product/";
export const useAdminStore = defineStore("admin", () => { 


  // 모든 유저 정보 조회
  const getAllUser = (role) => {
    console.log(role);
    return axios.get(`${ADMIN_API_URL}/user`,{params:{
      role
      }})
      .then(response => {
       return response.data;
      }).catch(err => {
        console.error("모든 유저 정보 조회 실패:", err);
      });
  };

  const userInfo = ref(null)
  // 유저 한명 정보 조회
  const getUser = (userId) => {
     axios.get(`${ADMIN_API_URL}/user/${userId}`)
         .then((res)=>{
           userInfo.value = res.data;
           console.log(userInfo.value);
         }).catch((err)=>{
           console.log("getUserErr");
           console.log(err);

     })
  };

  // 유저 등록
  const createUser = (user) => {
    return axios.post(`${ADMIN_API_URL}/user`, user);
  };

  // 유저 정보 수정
  const editUser = (user) => {
    return axios.put(`${ADMIN_API_URL}/user/${user.userId}`, user);
  };

  // 유저 삭제
  const deleteUser = (userId) => {
    return axios.delete(`${ADMIN_API_URL}/user/${userId}`);
  };

  // loading
    const loading = ref(false)


  // 전체 상품 조회
    const productList = ref([])
    const getProductList = async (inspection) => {
        const searchParams = {inspection};
        await axios
            .get(`${ADMIN_API_URL}/product`, {
                params: searchParams,
            })
            .then((res) => {

                productList.value = res.data;
                console.log(productList.value);
            }).catch((err)=>{
                console.log("getProductList 에러");
                console.log(err);
                alert('상품을 불러오는데 실패했습니다.');
                router.push('/')
            }).finally(()=>{
                loading.value = false;
            });
        loading.value = false;
    };

    // 상품 검수 상태 수정
    const updateInspectionStatus = (productCode, newStatus) => {
        console.log(productCode)
        console.log(newStatus)
        return axios.put(`${ADMIN_API_URL}/product/inspection`,{productCode,'inspection':newStatus}).catch((err)=>{
            console.log("UpdateInspectionErr")
            console.log(err);
        })
    }

    // 유저의 상품 목록 불러오기
    const userProductList = ref([])
    const getUserProductList = (userId) => {
        console.log(userId)
        axios.get(`${ADMIN_API_URL}/product/user/${userId}`).then((res) => {
            userProductList.value = res.data;
            console.log(userProductList.value);
        }).catch((err)=>{
            console.log("getUserProductListErr");
            console.log(err);
        })
    }
    //
    const adminProduct = ref({})
    const getProduct = (productCode) => {
        axios.get(`${ADMIN_API_URL}/product/${productCode}`).then((res) => {
            adminProduct.value = res.data;
            console.log(adminProductList.value);
        }).catch((err)=> {
            console.log("getProductErr");
            console.log(err);
        })
    }


    // 관리자 상품 수정
    const updateProduct = (updateProduct) => {
        console.log(updateProduct.value)
        return axios.put(`${ADMIN_API_URL}/product/${updateProduct.value.productCode}`, updateProduct.value).then((res) => {

        }).catch((err)=> {
            console.log("updateProductErr");
            console.log(err);
        })
    }

    // 관리자 상품 삭제
    const deleteProduct = (productCode) => {
        axios.delete(`${PRODUCT_API_URL}${productCode}`).then(() => {

        }).catch((e)=>{
            console.log('deleteProduct_err')
            console.log(e);
            alert('상품 삭제 실패')
            router.push('/')
        })
    }

  return {  getAllUser, getUser, createUser, editUser, deleteUser,userInfo,
      productList,getProductList, updateInspectionStatus ,getUserProductList,userProductList,getProduct,adminProduct,updateProduct ,deleteProduct};
});
