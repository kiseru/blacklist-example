<template>
  <div class="p-5">
    <main class="container">
      <div class="bg-white rounded shadow">
        <div class="p-2">
          <div class="p-2">
            <div class="text-center">
              <h1 class="h3">Login</h1>
            </div>
          </div>

          <div class="p-2" v-if="$store.state.error !== null">
            <div class="alert alert-danger">{{$store.state.error.response.data}}</div>
          </div>

          <div class="p-2">
            <div class="form-group">
              <label for="username">Username</label>
              <input type="text" class="form-control" id="username" v-model="user.username">
            </div>

            <div class="form-group">
              <label for="password">Password</label>
              <input type="password" class="form-control" id="password" v-model="user.password">
            </div>

            <button class="btn btn-primary btn-block" @click="login">Log In</button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        user: {
          username: "",
          password: ""
        }
      }
    },
    methods: {
      login() {
        this.$store.dispatch('login', this.user);
      }
    }
  }
</script>