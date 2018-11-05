<template>
  <div class="p-5">
    <main class="container">
      <div class="bg-white rounded shadow">
        <div class="p-2">
          <div class="p-2">
            <div class="text-center">
              <h1 class="h3">Users</h1>
            </div>
          </div>

          <div class="p-2">
            <div v-if="$store.state.users === null || $store.state.users.length === 0">
              <div class="text-center">
                <h1 class="h4">No Users</h1>
              </div>
            </div>
            <div v-else>
              <table class="table">
                <thead class="thead-dark">
                <tr>
                  <th>Username</th>
                  <th>Role</th>
                  <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="user in $store.state.users">
                  <td>{{user.username}}</td>
                  <td>{{user.role}}</td>
                  <td class="w-25">
                    <div class="d-flex">
                      <div class="px-1 w-50">
                        <button class="btn btn-primary btn-sm btn-block" v-if="user.role === 'USER'" @click="makeAdmin(user.username)">Make admin</button>
                        <button class="btn btn-primary btn-sm btn-block" v-else-if="user.role === 'ADMIN'" @click="makeUser(user.username)">Make user</button>
                      </div>
                      <div class="px-1 w-50">
                        <button class="btn btn-danger btn-sm btn-block" @click="block(user.username)">Block</button>
                      </div>
                    </div>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
  export default {
    methods: {
      makeUser(username) {
        this.$store.dispatch('makeUser', username);
      },
      makeAdmin(username) {
        this.$store.dispatch('makeAdmin', username);
      },
      block(username) {
        this.$store.dispatch('block', username);
      }
    },
    mounted() {
      this.$store.dispatch('getUsers');
    }
  }
</script>