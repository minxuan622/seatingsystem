<template>
  <div class="container mt-4">

    <!-- 座位圖 -->
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">
        <h2 class="h4 mb-0">座位配置圖</h2>
      </div>
      <div class="card-body">
        <div v-for="floor in groupedSeats" :key="floor.floorNo" class="floor-section mb-4">
          <h3 class="h5 mb-3">{{ floor.floorNo }}</h3>
          <div class="seat-grid">
            <button
              v-for="seat in floor.seats"
              :key="seat.seatSeq"
              :class="{
                'seat': true,
                'occupied': seat.empId,
                'available': !seat.empId,
                'selected': seat.seatSeq === selectedSeatSeq,
                [`floor-${floor.floorNo.replace(/\s+/g, '')}`]: true
              }"
              @click="selectSeat(seat)"
            >
              {{ seat.seatNo }} <br />
              <small v-if="seat.empId">({{ seat.empId }})</small>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 座位管理 -->
    <div class="card" v-if="selectedSeatSeq">
      <div class="card-header bg-info text-white">
        <h2 class="h4 mb-0">座位管理</h2>
      </div>
      <div class="card-body">
        <div v-if="selectedSeatInfo" class="mb-3">
          <p class="mb-2"><strong>選擇的座位:</strong> {{ selectedSeatInfo.floor }} {{ selectedSeatInfo.seatNo }} (編號: {{ selectedSeatInfo.seatSeq }})</p>

          <div v-if="selectedSeatInfo.empId" class="mb-3">
            <p class="alert alert-info">目前由 <strong>{{ selectedSeatInfo.empId }}</strong> 佔用</p>
            <button @click="clearSeat" class="btn btn-warning">
              <i class="bi bi-trash"></i> 清除座位
            </button>
          </div>

          <div v-else class="mb-3">
            <div class="form-group">
              <label for="employee" class="form-label">選擇員工:</label>
              <select v-model="selectedEmpId" id="employee" class="form-select mb-2">
                <option value="">請選擇員工</option>
                <option v-for="emp in employees" :key="emp.empId" :value="emp.empId">
                  {{ emp.empId }} - {{ emp.name }}
                </option>
              </select>
            </div>
            <button @click="assignSeat" class="btn btn-primary" :disabled="!selectedEmpId">
              <i class="bi bi-person-plus"></i> 指派座位
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 訊息提示 -->
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
      <div class="toast" ref="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header" :class="toastHeaderClass">
          <strong class="me-auto">{{ toastTitle }}</strong>
          <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">
          {{ toastMessage }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { Toast } from 'bootstrap'

export default {
  name: 'SeatingSystem',
  data() {
    return {
      seats: [],
      employees: [],
      selectedSeatSeq: null,
      selectedEmpId: '',
      toast: null,
      toastMessage: '',
      toastTitle: '',
      toastHeaderClass: '',
      apiBaseUrl: '/api' // 後端API位址
    }
  },
  computed: {
    groupedSeats() {
      const floors = {};
      this.seats.forEach(seat => {
        if (!floors[seat.floor]) {
          floors[seat.floor] = { floorNo: seat.floor, seats: [] };
        }
        floors[seat.floor].seats.push(seat);
      });
      return Object.values(floors);
    },
    selectedSeatInfo() {
      return this.seats.find(seat => seat.seatSeq === this.selectedSeatSeq);
    }
  },
  methods: {
    fetchSeats() {
      axios.get(`${this.apiBaseUrl}/seats`)
        .then(response => {
          this.seats = response.data;
        })
        .catch(error => {
          this.showToast('錯誤', '取得座位資料失敗: ' + this.getErrorMessage(error), 'bg-danger text-white');
        });
    },
    fetchEmployees() {
      axios.get(`${this.apiBaseUrl}/employees`)
        .then(response => {
          this.employees = response.data;
        })
        .catch(error => {
          this.showToast('錯誤', '取得員工資料失敗: ' + this.getErrorMessage(error), 'bg-danger text-white');
        });
    },
    selectSeat(seat) {
      this.selectedSeatSeq = seat.seatSeq;
      this.selectedEmpId = '';
    },
    assignSeat() {
      if (!this.selectedEmpId || !this.selectedSeatSeq) {
        this.showToast('警告', '請選擇員工和座位', 'bg-warning');
        return;
      }

      console.log("即將指派座位：", this.selectedEmpId, this.selectedSeatSeq);
      console.log("selectedEmpId 類型:", typeof this.selectedEmpId);

      // 使用表單數據格式發送請求
      const formData = new FormData();
      formData.append('empId', this.selectedEmpId);
      formData.append('seatSeq', this.selectedSeatSeq);

      axios.post(`${this.apiBaseUrl}/seats/assign`, formData)
        .then(() => {
          this.showToast('成功', '座位指派成功！', 'bg-success text-white');
          this.fetchSeats();
          this.selectedEmpId = '';
        })
        .catch(error => {
          this.showToast('錯誤', '指派座位失敗: ' + this.getErrorMessage(error), 'bg-danger text-white');
          console.error("API錯誤詳情:", error.response);
        });
    },
    clearSeat() {
      if (!this.selectedSeatSeq) {
        this.showToast('警告', '請選擇座位', 'bg-warning');
        return;
      }

      console.log("清除座位編號：", this.selectedSeatSeq); // ← 加這一行

      const formData = new FormData();
      formData.append('seatSeq', this.selectedSeatSeq);

      axios.post(`${this.apiBaseUrl}/seats/clear`, formData)
        .then(() => {
          this.showToast('成功', '座位清除成功！', 'bg-success text-white');
          this.fetchSeats();
        })
        .catch(error => {
          this.showToast('錯誤', '清除座位失敗: ' + this.getErrorMessage(error), 'bg-danger text-white');
        });
    },
    showToast(title, message, headerClass) {
      this.toastTitle = title;
      this.toastMessage = message;
      this.toastHeaderClass = headerClass;
      if (this.toast) {
        this.toast.show();
      }
    },
    getErrorMessage(error) {
      return error.response?.data || error.message || '未知錯誤';
    },
    initializeToast() {
      this.toast = new Toast(this.$refs.toast);
    }
  },
  created() {
    // 設置請求攔截器來確保內容類型設置正確
    axios.interceptors.request.use(config => {
      if (config.data instanceof FormData) {
        config.headers['Content-Type'] = 'multipart/form-data';
      }
      return config;
    });
  },
  mounted() {
    this.fetchSeats();
    this.fetchEmployees();
    this.initializeToast();
    // 確保初始化時沒有選中任何值
    this.selectedEmpId = '';
    this.selectedSeatSeq = null;
  }
}
</script>

<style scoped>
.seat-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;

  justify-content: center;
  align-items: center;
}

.seat {
  width: 90px;
  height: 70px;
  border-radius: 8px;
  font-size: 14px;
  border: 1px solid #ccc;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  transition: all 0.2s;
}

.seat:hover {
  transform: scale(1.05);
}

.available {
  background-color: #e0ffe0;
}

.occupied {
  background-color: #ffd6d6;
}

.selected {
  border: 3px solid #0000ff;
  box-shadow: 0 0 5px rgba(0, 0, 255, 0.5);
}

/* 不同樓層的顏色 */
.floor-1樓 .available { background-color: #e0ffe0; } /* 綠色調 */
.floor-2樓 .available { background-color: #e0e0ff; } /* 藍色調 */
.floor-3樓 .available { background-color: #ffe0e0; } /* 紅色調 */
.floor-4樓 .available { background-color: #ffffd0; } /* 黃色調 */

/* 已佔用座位統一樣式，但邊框可以有樓層顏色 */
.floor-1樓 .occupied { background-color: #ffd6d6; border-left: 4px solid #4caf50; }
.floor-2樓 .occupied { background-color: #ffd6d6; border-left: 4px solid #2196f3; }
.floor-3樓 .occupied { background-color: #ffd6d6; border-left: 4px solid #f44336; }
.floor-4樓 .occupied { background-color: #ffd6d6; border-left: 4px solid #ffeb3b; }

.floor-section {
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 8px;
}
</style>