/*
 * @Descripttion:
 * @version:
 * @Author: liuwl
 * @Date: 2021-01-11 18:03:39
 * @LastEditTime: 2021-04-25 09:31:22
 */
import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);

//store 中存放的就是全局共享数据
const state = {
  isCollapse: false,
  componentSize: "medium",
  componentBtnSize: "small",
  searchName: "",
  searchClick: false,
  cooperationId: "",
};
const mutations = {
  isCollapseChange(state) {
    state.isCollapse = !state.isCollapse;
  },
  searchNameChange(state, val) {
    return (state.searchName = val);
  },
  searchClickChange(state) {
    state.searchClick = !state.searchClick;
  },
  cooperationIdChange(state, val) {
    state.cooperationId = val;
  },
  componentSizeChange(state, val) {
    state.componentSize = val;
  },
  componentBtnSizeChange(state, val) {
    state.componentBtnSize = val;
  },
};
const actions = {};

export default new Vuex.Store({
  state,
  mutations,
  actions,
});
