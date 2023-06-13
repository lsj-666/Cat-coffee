<template>
  <div>
    <el-card
        class="main-card"
        shadow="hover"
        style="min-height: 600px"
    >
      <div
          slot="header"
          class="file-search-condition-title"
      >
        <span class="content-Breadcrumb">我的信息</span>
      </div>

      <el-form label-width="80px" size="small">
        <el-upload
            class="avatar-uploader"
            :action="'http://' + serverIp +':9090/file/upload'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            style="max-width: 300px;margin-left: 80px"
        >
          <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar" style="max-width: 200px;max-height: 200px">
          <i v-else class="el-icon-plus avatar-uploader-icon" style="max-height: 100px">请上传头像</i>
        </el-upload>

        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled autocomplete="off" style="max-width: 300px"></el-input>
        </el-form-item>
        <!--        <el-form-item label="昵称">-->
        <!--          <el-input v-model="form.nickname" autocomplete="off" style="max-width: 300px"></el-input>-->
        <!--        </el-form-item>-->
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off" style="max-width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off" style="max-width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="form.address" autocomplete="off" style="max-width: 300px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="modify">修改密码</el-button>
          <el-button type="primary" @click="save">保 存</el-button>

        </el-form-item>
      </el-form>
      <el-dialog
          :title="title"
          :close-on-click-modal='false'
          :visible.sync="rightVisible"
          :modal-append-to-body='false'
          append-to-body
          width="600px">
        <el-form
            :inline="true"
            :model="modifyForm"
            label-width="180px"
            label-position="right"
            ref="modifyForm"
            class="enter-from-submit"
            v-loading="loading"
            :rules="rules"
        >

          <el-row style="margin-bottom: 10px">
            <el-form-item label="原先密码" prop="password" >
              <el-input v-model="modifyForm.password" clearable filterable allow-create :size="componentSize"
                        type="password"
                        show-password placeholder="请输入初始密码"></el-input>
            </el-form-item>
          </el-row>

          <el-row style="margin-bottom: 10px">
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="modifyForm.newPassword" clearable filterable allow-create :size="componentSize"
                        type="password"
                        show-password
                        placeholder="请输入新密码"></el-input>
            </el-form-item>
          </el-row>

          <el-row style="margin-bottom: 10px">
            <el-form-item label="确认密码" prop="againPassword">
              <el-input v-model="modifyForm.againPassword" clearable filterable allow-create :size="componentSize"
                        type="password"
                        show-password
                        placeholder="请再次输入新密码"></el-input>
            </el-form-item>
          </el-row>


          <el-row>
            <el-col :span="10">
              <el-form-item>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-button type="primary" @click="modifySubmit('form')">确认修改</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-button @click="EditDialogNoSubmit">取消</el-button>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>

      </el-dialog>

    </el-card>

  </div>
</template>

<script>
import {serverIp} from "../../../public/config";
import rules from "../../assets/json/modifyPwdRules.json"

export default {
  name: "person",
  data() {
    return {
      serverIp: serverIp,
      form: {
        id: "",
        avatarUrl: "",
      },
      rules: {
        password: [
          {required: true, message: '请输入初始密码', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        againPassword: [
          {required: true, message: '请再次输入新密码', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      },
      title: "修改密码",
      modifyForm: {},
      rightVisible: false,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.$axios.getUsername(this.user.username).then(res => {
      this.form = res.object
      console.log("头像界面初始化：" + this.form.avatarUrl)

    })
  },
  methods: {
    modifySubmit() {

      this.$refs['modifyForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.modifyForm.password !== this.user.password) {
            this.$message.error("初始密码有误，请重新输入！")
            return false
          }
          if (this.modifyForm.newPassword !== this.modifyForm.againPassword) {
            this.$message.error("两次输入的密码不一致！")
            return false
          }
          this.user.password = this.modifyForm.newPassword;
          console.log("修改密码后的user：" + this.user.token)
          this.$axios.modifyPassword(this.user).then(res => {
            if(res.returnCode === '200') {
              this.$message.success("修改成功！请重新登录！")
              this.$store.commit("logout")
            } else {
              this.$message.error(res.returnMessage)
            }
          })
        }
      });
    },
    modify() {
      this.rightVisible = true;
    },
    //关闭文档编辑弹窗不提交
    EditDialogNoSubmit() {
      this.rightVisible = false;
    },
    save() {
      this.$axios.addUser(this.form).then(res => {
        if (res.returnCode == "200") {
          this.$message.success("保存成功")


          // 更新浏览器存储的用户信息
          this.$axios.getUsername(this.user.username).then(res => {
            res.object.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res.object))
          })

          // 触发父级更新User的方法
          this.$emit("refreshUser")

        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res) {
      console.log("上传头像res：" + res)
      this.form.avatarUrl = res

    }

  }
}
</script>

<style scoped>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
</style>