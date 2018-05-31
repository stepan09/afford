<template>
  <div class="container">
    <div class="row">
      <div class="col-md-8">
        <input type="text" v-model="search" class="form-control" placeholder="Пошук">
      </div>
      <div class="col-md-4">
        <button type="button" @click="showModal = true" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
          Додати
        </button>
      </div>
    </div>
    <br>

    <b-card  v-for="court in filteredList" :key="court.courtId" v-bind:data="court" :title="court.name"
             img-src="../static/img/court.jpg"
             img-alt="Image"
             img-top
             tag="article"
             style="max-width: 20rem;"
             class="mb-2">
      <p class="card-text">
        <p>Дата заснування: {{court.foundationDate}}</p>
        <p>Адреса: {{court.address}} </p>
        <p>Тип покриття: {{court.coverType}}</p>
      </p>
      <b-button variant="success" @click="showUpdateModals(court)">Змінити</b-button>
      <b-button @click="deleteCourt(court.courtId)" variant="danger">Видалити</b-button>
    </b-card>

    <!-- Modal -->
    <div v-if="showModal">
      <transition name="modal">
        <div class="modal-mask">
          <div class="modal-wrapper">
            <div class="modal-dialog sss">
              <div class="modal-content">
                <div class="modal-header">
                  Додати
                  <button type="button" class="close" @click="showModal=false">
                    <span aria-hidden="true">&times;</span>
                  </button>

                </div>
                <div class="modal-body">

                  <div class="form-group">
                    <label>Назва</label>
                    <input v-model="formAdd.name" type="text" class="form-control" placeholder="Введіть назву">
                  </div>

                  <div class="form-group">
                    <label>Дата заснування</label>
                    <input v-model="formAdd.foundationDate" type="date" class="form-control" >
                  </div>

                  <div class="form-group">
                    <label>Адреса</label>
                    <input v-model="formAdd.address" type="text" class="form-control" placeholder="Введіть адресу">
                  </div>

                  <div class="form-group">
                    <label>Тип покриття</label>
                    <input v-model="formAdd.coverType" type="text" class="form-control" placeholder="Введіть вмістимість">
                  </div>

                  <button class="btn btn-default" @click="showModal=false">Скасувати</button>
                  <button class="btn btn-success" @click="addCourt">Зберегти</button>

                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>

    <div v-if="showUpdateModal">
      <transition name="modal">
        <div class="modal-mask">
          <div class="modal-wrapper">
            <div class="modal-dialog sss">
              <div class="modal-content">
                <div class="modal-header">
                  Змінити
                  <button type="button" class="close" @click="showUpdateModal=false">
                    <span aria-hidden="true">&times;</span>
                  </button>

                </div>
                <div class="modal-body">

                  <div class="form-group">
                    <label>Назва</label>
                    <input v-model="formAdd.name" type="text" class="form-control" placeholder="Введіть назву">
                  </div>

                  <div class="form-group">
                    <label>Дата заснування</label>
                    <input v-model="formAdd.foundationDate" type="date" class="form-control" >
                  </div>

                  <div class="form-group">
                    <label>Адреса</label>
                    <input v-model="formAdd.address" type="text" class="form-control" placeholder="Введіть адресу">
                  </div>

                  <div class="form-group">
                    <label>Тип покриття</label>
                    <input v-model="formAdd.coverType" type="text" class="form-control" placeholder="Введіть вмістимість">
                  </div>

                  <button class="btn btn-default" @click="showUpdateModal=false">Скасувати</button>
                  <button class="btn btn-success" @click="updateCourt(formAdd)">Зберегти</button>

                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>

  </div>
</template>

<script>
  import axios from 'axios'
    export default {
        name: "Court",
      data: () => ({
        search: '',
        courts: [],
        formAdd: {},
        showModal: false,
        showUpdateModal: false
      }),
      created() {
        this.fetchCourts();
      },
      methods: {
        fetchCourts() {
          axios.get('http://localhost:8080/api/courts').then((response) => {
            this.courts = response.data;
          });
        },
        deleteCourt(id) {
          axios.delete('http://localhost:8080/api/courts/' + id).then(() => {
            this.fetchCourts();
          });
        },
        addCourt() {
          this.showModal = false;
          axios.post('http://localhost:8080/api/courts/', this.formAdd).then(() => {
            this.fetchCourts();
          });
          this.formAdd = {};
        },
        updateCourt(court) {
          this.showUpdateModal = false;
          console.log(this.formAdd);
          axios.put('http://localhost:8080/api/courts/' + court.courtId, court).then(() => {
            this.fetchCourts();
          });
          this.formAdd = {};
        },
        showUpdateModals(court) {
          this.showUpdateModal = true;
          this.formAdd = court;
        },
      },
      computed: {
        filteredList() {
          return this.courts.filter(court => {
            return court.name.toLowerCase().includes(this.search.toLowerCase());
          })
        }
      }
    }
</script>

<style scoped>
  .container {
    margin-top: 20px;
  }
  .card {
    display: inline-block;
    margin: 8px;
  }

  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
    overflow-y: scroll;
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }
</style>
