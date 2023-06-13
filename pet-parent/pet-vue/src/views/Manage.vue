<template>
  <el-container style="min-height: 100vh">

    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" style="padding-bottom: 20px" />
    </el-aside>

    <el-container>
      <el-header>
        <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse"  :user="user"/>
      </el-header>

      <el-main style="margin-top: 10px">
        <!--        表示当前页面的子路由会在 <router-view /> 里面展示-->
        <router-view @refreshUser="getUser" />
      </el-main>

    </el-container>
  </el-container>
</template>

<script>

import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Home',
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {}
    }
  },
  components: {
    Aside,
    Header
  },
  created() {
    // 从后台获取最新的User数据
    this.getUser()
  },
  methods: {
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      console.log("getUser父级方法123123123");
      if (username) {
        console.log("getUser父级方法");
        // 从后台获取User数据
        this.$axios.getUsername(username).then(res => {
          if (res.returnCode == "200"){
            console.log("getUsername"+this.user.avra);
            // 重新赋值后台的最新User数据
            this.user = res.object
          }else {
            this.$router.push("/login")
          }

        })
      }else {
        this.$router.push("/login")
      }
    }
  }
}
</script>

