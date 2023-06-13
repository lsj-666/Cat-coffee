import Vue from 'vue'
import App from './App.vue'
import router from './router'
//store
// import store from '../src/vuex/store'
import store from '../src/store'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/style/gloable.css';
import './assets/style/app.scss';
//引入封装好的axios
import * as axios from "./utils/contract.js";
import request from "@/utils/request";

Vue.config.productionTip = false

// //全局使用封装好的axios
Vue.prototype.$axios = axios;
Vue.prototype.request=request

Vue.use(ElementUI);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
