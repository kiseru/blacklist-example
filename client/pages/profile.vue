<template>
  <div class="p-5">
    <main class="controller">
      <div class="bg-white rounded shadow">
        <div class="p-2">
          <div class="p-2">
            <div class="text-center">
              <h1 class="h3">My Profile</h1>
            </div>
          </div>

          <div class="p-2" v-if="$store.state.currentUser !== null">
            <p>Username: {{$store.state.currentUser.username}}</p>
            <p>Role: {{$store.state.currentUser.role}}</p>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
  export default {
    mounted() {
      this.$store.dispatch('getCurrentUser');
    }
  }
</script>