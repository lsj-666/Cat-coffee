<template>

  <div>
    <el-form
        :inline="true"
        :model="form"
        label-width="120px"
        label-position="right"
        ref="form"
        :rules="rules"
        class="enter-from-submit"
        v-loading="loading"
    >

      <el-form-item label="用户名称" prop="username">
        <el-input v-model="form.username" clearable filterable allow-create :size="componentSize"
                  placeholder="请输入用户名称"></el-input>
      </el-form-item>

      <el-form-item label="角色" prop="role">
        <el-select v-model="form.role" :size="componentSize"  clearable placeholder="请选择用户角色">
          <el-option
              v-for="(item,index) in selectRole"
              :key="index"
              :label="item.name"
              :value="item.flag"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" clearable filterable allow-create :size="componentSize"
                  placeholder="请输入地址"></el-input>
      </el-form-item>

      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" clearable filterable allow-create :size="componentSize"
                  placeholder="请输入电话"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" clearable filterable allow-create :size="componentSize"
                  placeholder="请输入邮箱"></el-input>
      </el-form-item>

      <el-form-item label="姓别" prop="sex">
        <el-select v-model="form.sex" :size="componentSize"  clearable placeholder="请选择姓别">
          <el-option
              v-for="(item,index) in selectSex"
              :key="index"
              :label="item.name"
              :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-row >
        <el-col :span="10">
          <el-form-item>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item>
            <el-button type="primary" @click="onSubmit('form')">{{ submitName }}</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item>
            <el-button @click="addUserEditDialogNoSubmit">取消</el-button>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
  </div>
</template>

<script>
import {mapState} from "vuex";
import addUserRules from "@/assets/json/addUserRules.json";

export default {
  props: ["addUserData"],
  watch:{

  },
  created() {
    this.form = this.addUserData
    console.log(this.form)
    this.getSelectRole();
  },
  mounted() {
  },
  computed: {
    ...mapState(["componentSize", "componentBtnSize"]),
  },
  data(){
    return{
      loading : false,
      //校验规则
      rules: addUserRules,
      form: {
        username:"",
        phone:"",
        address:"",
        email:"",
        password:"",
        role:"",
        sex:"",
      },
      submitName: "确定",
      //角色下拉框
      selectRole :[],
      selectSex :[{
        name:'男',
        value:'1',
      },
        {
          name:'女',
          value:'0',
        }

      ],
    }
  },
  methods:{
    addUserEditDialogNoSubmit() {
      this.$emit("addUserEditDialogNoSubmit");
    },
    getSelectRole() {
      this.$axios.getSelectRole().then((res) => {
        console.log(res)
        this.selectRole = res.beans;
      });
    },
    onSubmit(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          console.log("addUser.onSubmit",this.form)
          this.$axios.addUser(this.form).then((res) => {
            if (res.returnCode == "200"){
              this.$emit("addUserEditDialogClose");
              this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 1000,
              });
            }else {
              this.$notify({
                title: "错误",
                message: res.returnMessage,
                type: "warning",
                duration: 1000,
              });
            }
          });

        }
      });
    },
    computed: {
      ...mapState(["componentSize", "componentBtnSize"]),
    },
  },
}
</script>

<style scoped>

</style>