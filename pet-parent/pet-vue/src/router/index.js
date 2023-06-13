import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Login/Register.vue')
    },
    {
        path: '/404',
        name: '404',
        component: () => import('../views/404.vue')
    },
    {
        path: '/',
        name: '',
        component: () => import('../views/Manage.vue'),
    },

    {
        path: '/about',
        name: 'about',
        component: () => import('../views/AboutView.vue')
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 提供一个重置路由的方法
export const resetRouter = () => {
    router.matcher = new VueRouter({
        mode: 'history',
        base: process.env.BASE_URL,
        routes
    })
}

export const setRouters = () => {
    const storeMenus = localStorage.getItem("menus");
    console.log("——————————————storeMenus:" + storeMenus);
    if (storeMenus) {
        // 获取当前的路由对象名称数组
        const currentRouteNames = router.getRoutes().map(v => v.name)
        if (!currentRouteNames.includes('Manage')) {
            // 拼装动态路由
            const manageRoute = {
                path: '/',
                name: 'Manage',
                component: () => import('../views/Manage.vue'),
                redirect: "/home",
                children: []
            }
            const menus = JSON.parse(storeMenus)
            menus.forEach(item => {
                console.log("——————————————menus:" + menus);
                if (item.path) {  // 当且仅当path不为空的时候才去设置路由
                    let itemMenu = {
                        path: item.path.replace("/", ""),
                        name: item.name,
                        component: () => import('../views/' + item.pagePath + '.vue')
                    }
                    manageRoute.children.push(itemMenu)
                } else if (item.children.length) {
                    item.children.forEach(item => {
                        if (item.path) {
                            let itemMenu = {
                                path: item.path.replace("/", ""),
                                name: item.name,
                                component: () => import('../views/' + item.pagePath + '.vue')
                            }
                            manageRoute.children.push(itemMenu)
                        }
                    })
                }
            })
            // 动态添加到现在的路由对象中去
            router.addRoute(manageRoute)
        }
    }
}

// 重置就再set一次路由
setRouters()


router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称
    store.commit("setPath")
    // debugger
    // 未找到路由的情况
    if (!to.matched.length) {
        const storeMenus = localStorage.getItem("menus")
        // debugger
        if (storeMenus) {
            // if (to.path == "/" || to.path == "/home"){
            //     next("/")
            // }else {
            //
            // }
            next("/404")
        } else {
            // 跳回登录页面
            next("/login")
        }
    }
    // 其他的情况都放行
    next()

})

// const originalPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push(location, onResolve, onReject) {
//     if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
//     return originalPush.call(this, location).catch(err => err)
// }

export default router
