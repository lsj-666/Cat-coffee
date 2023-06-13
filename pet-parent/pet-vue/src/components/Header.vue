<template>
  <div class="myHeader" >
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer;margin-left: 15px; margin-top: 15px; font-size: 20px" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 15px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
      <div style="display: inline-block">
        <img :src="user.avatarUrl" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 20px">
        <span style="margin-right: 10px">{{ user.username }}</span><i class="el-icon-arrow-down" style="margin-left: 5px;margin-right: 10px"></i>
      </div>

      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/person">修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/person">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    user: Object
  },
  watch:{
    currentPathName (newVal,oldVal){
      console.log(newVal)
    }
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据

    }
  },
  data() {
    return {
      // user : localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{}

    }
  },
  created() {
    // console.log(this.$route)
    // console.log(5555555555555)
    // console.log(this.user)
    // console.log(localStorage.getItem("user"))
    // console.log(JSON.parse(localStorage.getItem("user")))
    // console.log(this.user.username)
  },
  methods: {
    collapse() {
      // this.$parent.$parent.$parent.$parent.collapse()  // 通过4个 $parent 找到父组件，从而调用其折叠方法
      this.$emit("asideCollapse")
    },
    logout() {
      this.$store.commit("logout")
      console.log("退出登录")
      this.$message.success({
        message:'退出成功!',
        center:true
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.myHeader{
  font-size:12px; line-height: 60px; display: flex ; background-color: #ffffff;margin:2px -9px;border-radius: 5px;
}
</style>
