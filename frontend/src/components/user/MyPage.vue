<template>
  <div class="container">
    <ul class="nav nav-tabs">
      <li class="nav-item">
        <a class="nav-link active" aria-current="page">
          <div class="mypage-container">
            <h2>MyPage</h2>
            <div class="user-info" v-if="user">
              <div class="info-item">
                <label><strong>아이디:</strong></label>
                <p>{{ user.userId }}</p>
              </div>
              <div class="info-item">
                <label><strong>이름:</strong></label>
                <p>{{ user.userName }}</p>
              </div>
              <div class="info-item">
                <label><strong>이메일:</strong></label>
                <p>{{ user.userEmail }}</p>
              </div>
              <div class="info-item">
                <label><strong>별명:</strong></label>
                <p>{{ user.userNickname }}</p>
              </div>
            </div>
            <div class="user-actions">
              <button class="btn btn-outline-dark" @click="openEditProfile(user)">프로필 수정</button>
              <button class="btn btn-outline-danger" @click="quite(user.userId)">탈퇴</button>
            </div>

          </div>
          <div v-if="user && user.userRole === 'customer'" class="seller-prompt">
            상품을 판매하고 싶으시다면 <br/>
            <button @click="openSellerModal" class="link-btn">여기</button>를 클릭해서 판매자 정보를 입력하세요.
          </div>
        </a>
      </li>
      <li class="nav-item-2">
        <a class="nav-link"><CommonUserInfo/></a>
      </li>
    </ul>

    <!-- User Edit Profile Modal -->
    <div v-if="showEditProfile" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeUserModal">&times;</span>
        <h3>유저 정보 수정</h3>
        <form @submit.prevent="saveUser(editUser)">
          <div class="form-group">
            <label for="userId">아이디: </label>
            <input class="form-control" type="text" v-model="editUser.userId" disabled />
          </div>
          <div class="form-group">
            <label for="userPwd">비밀번호: </label>
            <input class="form-control" type="password" v-model="editUser.userPwd" />
          </div>
          <div class="form-group">
            <label for="userName">이름: </label>
            <input class="form-control" type="text" v-model="editUser.userName" />
          </div>
          <div class="form-group">
            <label for="userPhone">전화번호: </label>
            <input class="form-control" type="text" v-model="editUser.userPhone" />
          </div>
          <div class="form-group">
            <label for="userEmail">이메일: </label>
            <input class="form-control" type="text" v-model="editUser.userEmail" />
          </div>
          <div class="form-group">
            <label for="userNickname">별명: </label>
            <input class="form-control" type="text" v-model="editUser.userNickname" />
          </div>
          <div class="modal-actions">
            <button type="submit" class="btn btn-primary">정보 수정</button>
            <button type="button" class="btn btn-secondary" @click="closeUserModal">취소</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Seller Info Modal -->
    <div v-if="showSellerModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeSellerModal">&times;</span>
        <h3>판매자 정보 입력</h3>
        <form @submit.prevent="saveSellerInfo">
          <div class="form-group">
            <label for="bankName">은행명: </label>
            <input class="form-control" type="text" v-model="sellerInfo.bank" />
          </div>
          <div class="form-group">
            <label for="accountNumber">계좌번호: </label>
            <input class="form-control" type="text" v-model="sellerInfo.accountNumber" />
          </div>
          <div class="modal-actions">
            <button type="submit" class="btn btn-primary">정보 저장</button>
            <button type="button" class="btn btn-secondary" @click="closeSellerModal">취소</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import CommonUserInfo from "@/components/common/CommonUserInfo.vue";

const store = useUserStore();
const router = useRouter();

const user = ref();
const editUser = ref(null);
const showEditProfile = ref(false);
const showSellerModal = ref(false);
const sellerInfo = ref({
  accountNumber: '',
  bank: ''
});

const openEditProfile = (user) => {
  if (user) {
    editUser.value = { ...user }; // user 객체를 editUser에 복사
    showEditProfile.value = true;
  } else {
    console.error('유저 정보가 존재하지 않습니다.');
  }
};

const openSellerModal = () => {
  showSellerModal.value = true;
};

const closeSellerModal = () => {
  showSellerModal.value = false;
};

// 유저 정보 수정
const saveUser = (userInfo) => {
  console.log(userInfo)
  store.editUser(userInfo).then(() => {
    store.getUser(store.loginUser.userId).then((result) => {
      user.value = result;
      store.loginUser.userRole = result.userRole;
    });
    closeUserModal();
  }).catch(err => {
    console.error('유저 정보 수정 실패', err);
  });
};

// 수정 모달 닫기 함수 정의
const closeUserModal = () => {
  showEditProfile.value = false;
  editUser.value = null;
};

// 유저 탈퇴 기능
const quite = (userId) => {
  const confirmed = window.confirm("정말 탈퇴하시겠습니까?");
  if (confirmed) {
    store.deleteUser(userId).then(() => {
      console.log('탈퇴완');
      store.logout();
    }).catch(err => {
      console.error('유저 정보 삭제 실패', err);
    });
  }
};

// 판매자 정보 저장
const saveSellerInfo = () => {
  sellerInfo.value = {...sellerInfo.value,userId:user.value.userId}
  // 판매자 정보 저장 로직 구현 (API 호출 등)
  // 예시로, store에 saveSellerInfo 메서드가 있다고 가정
  store.saveSellerInfo(sellerInfo.value).then(() => {
    user.value.userRole="seller"
    saveUser(user.value)
    alert('판매자 정보 저장 완료');
    alert('다시 로그인하면 판매자 페이지가 나타납니다.' +
        '')
    closeSellerModal();
  }).catch(err => {
    console.error('판매자 정보 저장 실패', err);
  });
};

onMounted(() => {
  store.getUser(store.loginUser.userId).then((result) => {
    user.value = result;
  });
});
</script>

<style scoped>
.mypage {
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  font-family: 'EliceDXNeolli', sans-serif;
}
.mypage-container h2 {
  text-align: center;
  font-weight: bold;
  margin-top: 40px;
  margin-bottom: 40px;
}

.user-info {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.nav-item{
  margin-top: 20px;
  margin-left: 50px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.info-item label {
  font-weight: bold;
  flex-basis: 30%;
}

.info-item p {
  flex-basis: 70%;
  margin: 0;
}

.user-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.seller-prompt {
  margin-top: 20px;
  text-align: left;
}
.link-btn{
  border :none;
  background-color: white;
  text-decoration: underline;
}
.link-btn:hover{
  color: gray;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  position: relative;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  width: 80%;
  max-width: 500px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.25);
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 24px;
  cursor: pointer;
}

.modal-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.form-group {
  margin-bottom: 20px; /* 위 아래 간격 조정 */
}
</style>

