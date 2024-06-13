import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useRouter } from "vue-router";
import {useUserStore} from "@/stores/userStore.js";

const PRODUCT_API_URL = "http://localhost:8080/product/";
const CART_API_URL = "http://localhost:8080/cart";


export const useProductStore = defineStore("product", () => {
  const productList = ref([]);
  const userStore = useUserStore();
  const router = useRouter();
  const searchParams = ref({
    productCategory: "ALL",
    searchType: "none",
    keyword : ""
  });

  const loading = ref(false);

  const getProductList = async (categoryCode) => {
    loading.value = true;
    if (categoryCode) {
      searchParams.value.productCategory = categoryCode;
    } else {
      searchParams.value.productCategory = "ALL";
    }
    searchParams.value = {...searchParams.value , inspection:'검수완료'};
    await axios
      .get(PRODUCT_API_URL, {
        params: searchParams.value,
      })
      .then((res) => {
        productList.value = res.data;
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

  const product = ref(null);

  const images = ref([]);

  const getProduct = async (productCode) => {
    loading.value = true;
    await axios
      .get(`${PRODUCT_API_URL}${productCode}`)
      .then((res) => {

        product.value = res.data;
        axios
          .get(`${PRODUCT_API_URL}img/${productCode}`)
          .then((res) => {
            images.value = res.data;
          })
          .catch((err) => {
            console.log('getProduct=>img')
            console.log(err);
            alert('이미지를 불러오는데 실패했습니다.')
            images.value = null;
          });
      })
      .then(() => {
        loading.value = false;
      }).catch((err)=>{
        console.log("getProduct");
        console.log(err);
        alert("상품을 불러오는데 실패했습니다.")
        router.push('/');
        }).finally(()=>{
          loading.value = false;
        });
    loading.value = false
  };

  const createProduct = (product, thumbnail, imageFiles) => {

    axios
      .post(`${PRODUCT_API_URL}`, product)
      .then((res) => {
        if (thumbnail !== null) {
          const formData = new FormData();
          formData.append("thumbnail", thumbnail);
          axios
            .post(`${PRODUCT_API_URL}thumbnail/${res.data}`, formData, {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            })
            .then(() => {}).catch((err)=>{
              console.log('post_product_thumbnail_err')
              console.log(err);
          });
        }
        if (imageFiles.length) {
          const formData = new FormData();
          imageFiles.forEach((img) => {
            formData.append("files", img);
          });

          axios
            .post(`${PRODUCT_API_URL}img/${res.data}`, formData, {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            })
            .then(() => {}).catch((err)=>{
              console.log('post_product_img_err')
              console.log(err);

          });
        }
      })
      .then(() => {
        router.push("/");
      }).catch((err)=>{
        console.log('post_product_err')
      console.log(err);
      router.push("/");
    });
  };

  const updateProduct = (productCode,thumbnail,imageFiles) => {

    axios.delete(`${PRODUCT_API_URL}img/${productCode}`).then().catch(()=>{}).finally(() => {
      axios.put(`${PRODUCT_API_URL}${productCode}`, product.value).then((res) => {
      if (thumbnail !== null) {
        const formData = new FormData();
        formData.append("thumbnail", thumbnail);
        axios
            .post(`${PRODUCT_API_URL}thumbnail/${productCode}`, formData, {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            })
            .then(() => {
            }).catch((err) => {
          console.log('PUT_product_thumbnail_err')
          console.log(err);
        });
      }
      if (imageFiles.length) {
        const formData = new FormData();
        imageFiles.forEach((img) => {
          formData.append("files", img);
        });
        axios
            .post(`${PRODUCT_API_URL}img/${productCode}`, formData, {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            })
            .then(() => {
            }).catch((err) => {
          console.log('Put_product_img_err')
          console.log(err);

        });
      }
      })
    }).then(()=>{
      router.push('/')
    })
  };
      const deleteProduct = (productCode) => {
    axios.delete(`${PRODUCT_API_URL}${productCode}`).then(() => {
      router.push('/')
    }).catch((e)=>{
      console.log('deleteProduct_err')
      console.log(e);
      alert('상품 삭제 실패')
      router.push('/')
    })
  }

  const cart = ref({
    userId: userStore.loginUser?userStore.loginUser.userId:null,
    productCode:product?product.productCode:null,
  })
  const addCart = (productCode) => {
        if(!productCode || !userStore.loginUser){
          alert('관심 상품 등록에 실패했습니다.')
          router.push('/')
        }
        cart.value.productCode = productCode;
        cart.value.userId = userStore.loginUser.userId;
        console.log("cart : ")
        console.log(cart.value)
        axios.post(CART_API_URL,cart.value).then((res) => {
          alert('관심상품에 추가되었습니다.')
        }).catch((err)=>{
          console.log('post_product_Cart_err')
          console.log(err);
        })
  }
  const deleteCart = (cart) => {

        axios.delete(CART_API_URL,{params:{"cartId":cart.cartId?cart.cartId:-1,"productCode":cart.productCode,"userId":cart.userId?cart.userId:""}}).then(() => {
          selectCartList(userStore.loginUser.userId);
          alert('관심 상품에서 제거되었습니다.')

        }).catch((err)=> {
          console.log('DELETE_CART_err')
          console.log(err)
        })
  }

  const cartList = ref([])

  // 관심 상품 목록 가져오기
  const isLike = ref(false)
  const selectCartList = (userId) => {
        return axios.get(`${CART_API_URL}/${userId}`).then((res)=>{
          cartList.value = res.data
          return res.data
          // isLike.value = cartList.value.some(cartItem => cartItem.productCode === productCode)
        }).catch((err)=>{
          console.log('GET_product_Cart_err')
          console.log(err);
        })
  }


  const likeCntOrderProductList = ref([])
  const latestOrderProductList = ref([])

  const initPage = () => {
        const condition = {
          keyword:"",
          searchType:"like",
          inspection:"검수완료",
          limit:5
        }
    axios.get(`${PRODUCT_API_URL}`,{
      params: condition,
    }).then((res) => {
      likeCntOrderProductList.value = res.data;
      console.log(likeCntOrderProductList.value)
    }).catch((err)=>{

      console.log(err)
    })
    condition.searchType = "latest";
    axios.get(`${PRODUCT_API_URL}`,{
      params: condition,
    }).then((res) => {
      latestOrderProductList.value = res.data;
      console.log(latestOrderProductList.value)
    }).catch((err)=>{
      console.log(err)
    })

  }




  return {
    productList,
    getProductList,
    product,
    getProduct,
    createProduct,
    images,
    loading,
    updateProduct,
    deleteProduct,
    addCart,
    deleteCart,
    selectCartList,
    isLike,searchParams,
    cartList,
    initPage,
    likeCntOrderProductList,
    latestOrderProductList


  };
});
