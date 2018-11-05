import Vue from 'vue';
import Vuex from 'vuex';

import axios from 'axios';
import cookie from 'js-cookie';

Vue.use(Vuex);

const store = () => new Vuex.Store({
  state: {
    users: [],
    error: null,
    currentUser: null
  },
  actions: {
    login(context, body) {
      axios
        .post('/api/login', body)
        .then(response => {
          cookie.set('authToken', response.data.value);
          window.location = '/profile';
        }).catch(error => context.state.error = error);
    },
    signUp(context, body) {
      axios
        .post('/api/sign_up', body)
        .then(response => {
          cookie.set('authToken', response.data.value);
          window.location = '/profile';
        });
    },
    getUsers(context) {
      axios
        .get('/api/users', {
          headers: {
            authToken: cookie.get('authToken')
          }
        })
        .then(response => {
          if (response.status === 200) {
            console.log(context);
            context.state.users = response.data;
          }
        })
    },
    makeAdmin(context, username) {
      axios
        .put('/api/admin/make_admin', {}, {
          params: {
            username
          },
          headers: {
            authToken: cookie.get('authToken')
          }
        }).then(() => context.dispatch('getUsers'))
    },
    makeUser(context, username) {
      axios
        .put('/api/admin/make_user', {}, {
          params: {
            username
          },
          headers: {
            authToken: cookie.get('authToken')
          }
        }).then(() => context.dispatch('getUsers'))
    },
    block(context, username) {
      axios
        .put('/api/admin/block', {}, {
          params: {
            username
          },
          headers: {
            authToken: cookie.get('authToken')
          }
        })
    },
    getCurrentUser(context) {
      axios
        .get('/api/users/me', {
          headers: {
            authToken: cookie.get('authToken')
          }
        }).then(response => context.state.currentUser = response.data)
        .catch(error => context.state.error = error);
    }
  }
});

export default store;