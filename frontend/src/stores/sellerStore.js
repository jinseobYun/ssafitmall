import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";
import { useUserStore } from "@/stores/userStore.js";

const SELLER_API_URL = "http://localhost:8080/seller";

export const useSellerStore = defineStore("seller", () => {
    const products = ref([]);
    const product = ref(null); 

    const userStore = useUserStore();

    // 판매 상품 모두 조회
    const getAllProducts = async () => {
        try {
            const res = await axios.get(`${SELLER_API_URL}/product`);
            products.value = res.data;
            return res.data;
        } catch (err) {
            console.error(err);
            alert("상품을 불러오는데 실패했습니다.");
            router.push("/");
        }
    };

    // 판매 상품 하나 조회
    const getProduct = async (productCode) => {
        try {
            const res = await axios.get(`${SELLER_API_URL}/product/${productCode}`);
            product.value = res.data;
            return res.data;
        } catch (err) {
            console.error(err);
            alert("상품을 불러오는데 실패했습니다.");
            router.push("/");
        }
    };

    // 판매 상품 등록
    const createProduct = async (product, thumbnail, imageFiles) => {
        try {
            const res = await axios.post(`${SELLER_API_URL}/product`, product);
            const productCode = res.data; // 서버에서 반환하는 productCode 사용
            if (thumbnail) {  // 썸네일 이미지가 존재하는 경우
                const formData = new FormData();  // FormData 객체를 생성
                formData.append("thumbnail", thumbnail);  // 썸네일 이미지를 FormData 객체에 추가
                await axios.post(`${SELLER_API_URL}/thumbnail/${productCode}`, formData, {  // 썸네일 이미지를 서버에 POST 요청으로 업로드
                    headers: {
                        "Content-Type": "multipart/form-data",  // 요청 헤더에 Content-Type을 multipart/form-data로 설정
                    },
                });
            }
            if (imageFiles.length) {  // 이미지 파일이 존재하는 경우
                const formData = new FormData();  // FormData 객체를 생성
                imageFiles.forEach((img) => {  // 각 이미지 파일을 FormData 객체에 추가
                    formData.append("files", img);
                });
                await axios.post(`${SELLER_API_URL}/img/${productCode}`, formData, {  // 이미지 파일들을 서버에 POST 요청으로 업로드
                    headers: {
                        "Content-Type": "multipart/form-data",  // 요청 헤더에 Content-Type을 multipart/form-data로 설정
                    },
                });
            }
        } catch (err) {
            console.error("판매 상품 등록 중 오류가 발생했습니다.", err);
            alert("상품 등록에 실패했습니다.");
        }
    };

    // 판매 상품 수정 
    const editProduct = async (productCode, product, thumbnail, imageFiles) => {
        try {        
            
            console.log("수정할 상품 코드:", productCode);
            console.log("수정할 상품 데이터:", product);

            await axios.delete(`${SELLER_API_URL}/img/${productCode}`);
            await axios.put(`${SELLER_API_URL}/product/${productCode}`, product);

            if (thumbnail) {
                const formData = new FormData();
                formData.append("thumbnail", thumbnail);
                await axios.post(`${SELLER_API_URL}/thumbnail/${productCode}`, formData, {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                });
            }

            if (imageFiles.length) {
                const formData = new FormData();
                imageFiles.forEach((img) => {
                    formData.append("files", img);
                });
                await axios.post(`${SELLER_API_URL}/img/${productCode}`, formData, {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                });
            }
        } catch (err) {
            console.error("상품 수정 중 오류가 발생했습니다.", err);
            alert("상품 수정에 실패했습니다.");
        }
    };

    // 판매 상품 삭제
    const deleteProduct = async (productCode) => {
        try {
            await axios.delete(`${SELLER_API_URL}/product/${productCode}`);
        } catch (error) {
            console.error('deleteProduct_err', error);
            alert('상품 삭제 실패');
            router.push('/');
        }
    };

    return { getAllProducts, getProduct, createProduct, editProduct, deleteProduct, products, product };
});
