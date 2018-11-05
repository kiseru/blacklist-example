<template>
  <div class="p-5">
    <main class="container">
      <div class="bg-white rounded shadow">
        <div class="p-2">
          <div class="p-2">
            <div class="text-center">
              <h1 class="h3">Sign Up</h1>
            </div>
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

            <button class="btn btn-primary btn-block" @click="signUp">Sign Up</button>
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
      signUp() {
        this.$store.dispatch('signUp', this.user)
      }
    }
  }
</script>