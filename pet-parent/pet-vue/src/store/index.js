import Vue from 'vue'
import Vuex from 'vuex'
import router, {resetRouter} from "@/router";

Vue.use(Vuex)


const store = new Vuex.Store({
    state: {
        currentPathName: '',
        isCollapse: false,
        componentSize: "medium",
        componentBtnSize: "small",
        searchName: "",
        searchClick: false,
        cooperationId: "",
    },
    mutations: {
        setPath (state) {
            state.currentPathName = localStorage.getItem("currentPathName")
        },
        logout() {
            // 清空缓存
            localStorage.removeItem("user")
            localStorage.removeItem("menus")
            router.push("/login")

            console.log("重置路由")
            // 重置路由
            resetRouter()
        },
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
    },
})

export default store
