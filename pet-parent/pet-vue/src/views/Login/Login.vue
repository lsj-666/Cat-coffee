<template>
  <div class="box1" style="height: 100%">
    <p style="color: rgb(0,0,0,0)">a</p>
    <div
        style="margin: 18% auto;margin-right: 17%;background-color: #fff; width: 350px; height: 280px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <span style="color: #2b85e4;cursor: pointer;margin-right: 100px;text-decoration: underline" @click="forgetPwd">忘记密码？</span>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {setRouters} from "@/router";

export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  created() {
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.$axios.login(this.user).then(res => {
            if (res.returnCode === '200') {
              localStorage.setItem("user", JSON.stringify(res.object))  // 存储用户信息到浏览器
              localStorage.setItem("menus", JSON.stringify(res.object.menus))  // 存储菜单信息到浏览器
              localStorage.setItem("currentPathName", JSON.stringify(res.object.menus[0].name))  // 存储菜单信息到浏览器
              // localStorage.setItem("menus", JSON.stringify(res.data.menus))  // 存储用户信息到浏览器

              // 动态设置当前用户的路由
              setRouters()

              this.$message.success({
                message: '登陆成功！欢迎您！' + res.object.username,
                center: true
              });

              // router.push("/Home")
              this.$router.push("/home")

            } else {
              this.$message.error(res.returnMessage)
            }
          })
        }
      });
    },
    forgetPwd(){
      this.$message.error({
        message: '忘记密码请您致电12138！或者到店请求店员帮助！',
        center: true,
        duration:5000,
      });
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  overflow: hidden;
}

.box1 {
  background: url(../../assets/img/backGround.jpg) no-repeat;
  min-width: 100%;
  min-height: 100%;
  background-attachment: fixed;
  background-size: 100% 100%;
}
</style>
