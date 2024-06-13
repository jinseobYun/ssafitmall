<template>
  <div class="admin-page">
    <h2 class="title"><strong>관리자 페이지</strong></h2>
    <!-- 유저 등록 -->
    <button @click="openRegistModal" class="btn">신규 유저 등록</button>
    <select v-model="role" class="role-select">
      <option value="ALL" selected>전체</option>
      <option value="seller">판매 유저</option>
      <option value="customer">구매 유저</option>
    </select>

    <div v-if="showRegistModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeRegisterModal">&times;</span>
        <h3 class="modal-head"><strong>신규 유저 등록</strong></h3>
        <form @submit.prevent="registUser">
          <label for="userId">아이디: </label>
          <input type="text" v-model="newUser.userId" /><br>
          <label for="userPwd">비밀번호: </label>
          <input type="password" v-model="newUser.userPwd" /><br>
          <label for="userName">이름: </label>
          <input type="text" v-model="newUser.userName" /><br>
          <label for="userPhone">전화번호: </label>
          <input type="text" v-model="newUser.userPhone" /><br>
          <label for="userEmail">이메일: </label>
          <input type="text" v-model="newUser.userEmail" /><br>
          <label for="userRole">역할: </label>
          <input type="text" v-model="newUser.userRole" /><br>
          <label for="userNickname">별명: </label>
          <input type="text" v-model="newUser.userNickname" /><br>
          <button type="submit" class="btn">유저 등록</button>
          <button type="button" class="btn btn-secondary" @click="closeRegisterModal">취소</button>
        </form>
      </div>
    </div>

    <div v-if="loading">
      <LoadingSpinner/>
    </div>

    <div v-else-if="users.length > 0">
      <table class="styled-table">
        <thead>
          <tr>
            <th scope="col">Id</th>
            <th scope="col">Pwd</th>
            <th scope="col">Name</th>
            <th scope="col">Phone</th>
            <th scope="col">Email</th>
            <th scope="col">Role</th>
            <th scope="col">Nickname</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in paginatedUsers" :key="user.userId">
            <td><RouterLink :to="`/adminpage/userDetail/${user.userId}`">{{ user.userId }}</RouterLink></td>
            <td>{{ user.userPwd }}</td>
            <td>{{ user.userName }}</td>
            <td>{{ user.userPhone }}</td>
            <td>{{ user.userEmail }}</td>
            <td>{{ user.userRole }}</td>
            <td>{{ user.userNickname }}</td>
            <td>
              <button type="button" class="btn btn-small" @click="openEditModal(user)">유저 정보 수정</button>



              <button type="button" class="btn btn-small btn-danger" @click="showConfirmDialog(user.userId)">유저 삭제</button>
              <ConfirmDialog
                  v-if="isDialogVisible"
                  :message="`정말 ${selectUserId} 님의 계정을 삭제하시겠습니까?`"
                  :visible="isDialogVisible"
                  :userId="user.userId"
                  @confirm="handleConfirm"
                  @cancel="handleCancel"
              />
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button class="btn" :disabled="currentPage === 1" @click="prevPage">이전</button>
        <span>{{ currentPage }} / {{ totalPages }}</span>
        <button class="btn" :disabled="currentPage === totalPages" @click="nextPage">다음</button>
      </div>
    </div>

    <div v-else>
      <p>등록된 유저가 없습니다...</p>
    </div>

    <!-- 유저 정보 수정 모달 -->
    <div v-if="showEditModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeUserModal">&times;</span>
        <h3 class="modal-head"><strong>유저 정보 수정</strong></h3>
        <form @submit.prevent="saveUser">
          <label for="userId">아이디: </label>
          <input type="text" v-model="editUser.userId" disabled /><br>
          <label for="userPwd">비밀번호: </label>
          <input type="password" v-model="editUser.userPwd" /><br>
          <label for="userName">이름: </label>
          <input type="text" v-model="editUser.userName" /><br>
          <label for="userPhone">전화번호: </label>
          <input type="text" v-model="editUser.userPhone" /><br>
          <label for="userEmail">이메일: </label>
          <input type="text" v-model="editUser.userEmail" /><br>
          <label for="userRole">역할: </label>
          <input type="text" v-model="editUser.userRole" /><br>
          <label for="userNickname">별명: </label>
          <input type="text" v-model="editUser.userNickname" /><br>
          <button type="submit" class="btn">정보 수정</button>
          <button type="button" class="btn btn-secondary" @click="closeUserModal">취소</button>
        </form>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue';
import { useAdminStore } from '@/stores/adminStore';
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ConfirmDialog from "@/components/common/Confirm.vue";

const store = useAdminStore();
const users = ref([]);
const editUser = ref(null);
const showRegistModal = ref(false);
const showEditModal = ref(false);
const newUser = ref({
  userId: '',
  userPwd: '',
  userName: '',
  userPhone: '',
  userEmail: '',
  userRole: '',
  userNickname: '',
});
const role = ref('ALL');
const currentPage = ref(1);
const itemsPerPage = 6;
const loading = ref(false);

// 유저 등록 모달 열기
const openRegistModal = () => {
  showRegistModal.value = true;
};

// 유저 등록 모달 닫고 입력값 초기화
const closeRegisterModal = () => {
  showRegistModal.value = false;
  newUser.value = {
    userId: '',
    userPwd: '',
    userName: '',
    userPhone: '',
    userEmail: '',
    userRole: '',
    userNickname: '',
  };
};

// 유저 등록
const registUser = () => {
  store.createUser(newUser.value).then(() => {
    getUsers();
    closeRegisterModal();
  }).catch(err => {
    console.error('유저 정보 등록 실패', err);
  });
};

// 유저 정보 가져오기
const getUsers = async () => {
  loading.value = true;
  try {
    const res = await store.getAllUser(role.value);
    users.value = res;
  } catch (err) {
    console.error('유저 정보 가져오기 실패', err);
  } finally {
    loading.value = false;
  }
};

// 유저 정보 모달 열기
const openEditModal = (user) => {
  editUser.value = { ...user };
  showEditModal.value = true;
};

// 유저 정보 수정
const saveUser = () => {
  store.editUser(editUser.value).then(() => {
    getUsers();
    closeUserModal();
  }).catch(err => {
    console.error('유저 정보 수정 실패', err);
  });
};

// 수정 모달 닫기 함수 정의
const closeUserModal = () => {
  showEditModal.value = false;
  editUser.value = null;
};

// 유저 삭제 기능
const deleteUser = (userId) => {
  store.deleteUser(userId).then(() => {
    getUsers();
  }).catch(err => {
    console.error('유저 정보 삭제 실패', err);
  });
};

// 페이지네이션 관련 함수
const paginatedUsers = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return users.value.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil(users.value.length / itemsPerPage);
});

const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--;
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) currentPage.value++;
};

onMounted(() => {
  getUsers();
});

watch(role, (next, prev) => {
  getUsers();
});

const isDialogVisible = ref(false);
const selectUserId = ref("")

function showConfirmDialog(userId) {
  selectUserId.value = userId
  isDialogVisible.value = true;
}
function handleConfirm() {
  deleteUser(selectUserId.value)
  isDialogVisible.value = false;
  selectUserId.value = "";
  console.log('확인됨');
}

function handleCancel() {
  isDialogVisible.value = false;

  console.log('취소됨');
}


</script>

<style scoped>
.title {
  margin-top: 30px;
}

.admin-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Noto Sans KR', sans-serif;
  color: #333;
}

.admin-page h2 {
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
  color: #222;
  font-family: 'EliceDXNeolli', sans-serif;
}

.btn {
  background-color: rgb(36, 208, 255);
  color: #fff;
  border: none;
  padding: 10px 20px;
  margin: 10px 5px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
  font-family: 'EliceDXNeolli', sans-serif;
}

.btn:hover {
  background-color: rgb(36, 208, 255);
}

.btn-small {
  padding: 5px 10px;
}

.btn-secondary {
  background-color: #ccc;
  color: #333;
}

.btn-danger {
  background-color: #f0786a;
  color: #fff;
}

.role-select {
  margin-left: 10px;
  padding: 5px;
  border-radius: 5px;
  font-family: 'EliceDXNeolli', sans-serif;
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 16px;
  text-align: left;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.styled-table thead tr {
  background-color: rgb(36, 208, 255);
  color: #ffffff;
  text-align: left;
  font-weight: bold;
}

.styled-table th, .styled-table td {
  padding: 12px 15px;
}

.styled-table tbody tr {
  border-bottom: 1px solid #dddddd;
}

.styled-table tbody tr:nth-of-type(even) {
  background-color: #f3f3f3;
}

.styled-table tbody tr:last-of-type {
  border-bottom: 2px solid rgb(36, 208, 255);
}

.styled-table tbody tr.active-row {
  font-weight: bold;
  color: rgb(36, 208, 255);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.pagination .btn {
  margin: 0 5px;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  font-family: 'EliceDXNeolli', sans-serif;
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-head{
  margin: auto;
  margin-top: 20px;
  margin-bottom: 20px;
  justify-content: center;
  align-items: center;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 20px;
  cursor: pointer;
}

/* 유저 정보 수정 모달 CSS 추가 */
.modal-content form {
  display: flex;
  flex-direction: column;
}

.modal-content form label {
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

.modal-content form input {
  margin-bottom: 15px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.modal-content form button {
  margin-top: 10px;
}
</style>
