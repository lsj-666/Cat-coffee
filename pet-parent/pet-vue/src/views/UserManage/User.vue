<template>
  <div>
    <el-card
        class="main-card"
        shadow="hover"
        style="min-height: 500px"
    >
      <div
          slot="header"
          class="file-search-condition-title"
      >
        <span class="content-Breadcrumb">用户管理</span>
      </div>

      <el-row>
        <el-col :span="24">
          <el-form
              :inline="true"
              :model="form"
              class="select-no-form"
              label-width="100px"
          >

            <el-form-item label="用户名称" prop="username">
              <el-input v-model="form.username" clearable filterable allow-create :size="componentSize"
                        placeholder="请输入用户名称"></el-input>
            </el-form-item>

            <el-form-item label="角色" prop="role">
              <el-select v-model="form.role" :size="componentSize"  clearable placeholder="请选择角色">
                <el-option
                    v-for="(item,index) in selectRole"
                    :key="index"
                    :label="item.name"
                    :value="item.flag"
                >
                </el-option>
              </el-select>
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

            <el-form-item class="el-form-item-button">
              <el-button
                  type="primary"
                  :size="componentBtnSize"
                  icon="el-icon-search"
                  @click="onSubmit"
              >查询
              </el-button>

              <el-button
                  :size="componentBtnSize"
                  icon="el-icon-finished"
                  @click="reOnSubmit"
              >重置
              </el-button>
            </el-form-item>

          </el-form>
        </el-col>
      </el-row>

      <el-col span:1>
        <el-form
            :inline="true"
            class="select-no-form"
            label-width="70px"
        >
          <el-button
              type="primary"
              style="margin: 0 0px 10px 0"
              :size="componentBtnSize"
              icon="el-icon-plus"
              @click="addUser"
          >新增用户
          </el-button>
          <el-popconfirm
              title="确定删除这些数据吗？"
              @confirm="deleteAll"
              style="margin: 0 10px 0 10px"
          >
            <el-button
                icon="el-icon-delete"
                slot="reference"
                type="danger"
                :size="componentBtnSize"
            >批量删除
            </el-button>
          </el-popconfirm>
          <el-upload :action="'http://' + serverIp + ':9090/user/import'" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
            <el-button
                type="primary"
                :size="componentBtnSize"
                icon="el-icon-upload2"
                @click="uploadFile"
            >导入
            </el-button>
          </el-upload>
          <el-button
              style="margin-left: 10px"
              type="primary"
              :size="componentBtnSize"
              icon="el-icon-download"
              @click="downloadFile"
          >导出
          </el-button>

          <el-table
              :data="tableData"
              v-loading="loading"
              ref="tableData"
              border
              @selection-change="handleSelectionChange"
              :header-cell-style="{background:'#e4e7ed','text-align': 'center'}"
              :row-class-name="tableRowClassName"
          >

            <el-table-column
                type="selection"
                width="40"
            >
            </el-table-column>
            <el-table-column
                prop="username"
                label="用户名称"
                width="calc(15%)"
                min-width="100px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                label="密码"
                width="calc(15%)"
                min-width="100px"
                align="center"
                show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-popconfirm title="确定重置该用户的密码吗？" @confirm="resetPassword(scope)">
                  <el-button
                      slot="reference"
                      class="assets-close-Ass"
                      type="success"
                      style="margin: 0 10px 0px 0"
                      :size="componentBtnSize"
                  >重置密码
                  </el-button>
                </el-popconfirm>
              </template>
            </el-table-column>
            <el-table-column
                prop="address"
                label="地址"
                width="calc(15%)"
                min-width="260px"
                align="left"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="phone"
                label="电话"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="email"
                label="邮箱"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="sex"
                label="姓别"
                width="calc(15%)"
                min-width="100px"
                align="center"
                show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span v-if="scope.row.sex == '1'">男</span>
                <span v-else-if="scope.row.sex == '0'">女</span>
              </template>
            </el-table-column>
            <el-table-column
                prop="name"
                label="角色"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                label="操作"
                fixed="right"
                align="center"
                width="120"
            >
              <template slot-scope="scope">
                <el-col :span="24">
                  <div>
                    <span @click="modifyUser(scope)">
                      <template>
                        <span style="color: #409eff; cursor: pointer;margin-right: 12px">编辑</span>
                      </template>
                    </span>
                    <el-popconfirm title="确定删除该条数据吗？" @confirm="deleteUser(scope)">
              <span
                  slot="reference"
                  class="assets-close-Ass"
                  style="
                  word-break: break-all;
                  text-overflow: ellipsis;
                  overflow: hidden;
                  white-space: nowrap;
                  color: #409eff; cursor: pointer;
                "
              >删除
              </span>
                    </el-popconfirm>
                  </div>
                </el-col>
              </template>
            </el-table-column>

          </el-table>

          <el-pagination
              @size-change="handleSizeChange"
              @current-change="pageChange"
              :current-page.sync="page"
              :page-sizes="[10, 30, 50]"
              :page-size="10"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              class="pagination-center"
          >
          </el-pagination>
        </el-form>
      </el-col>

      <el-dialog
          :title="addUserTitle"
          width="800px"
          :visible.sync="addUserVisible"
          :modal-append-to-body="false"
          :close-on-click-modal="false"
          append-to-body
      >
        <addUser
            v-if="addUserVisible"
            :addUserData="addUserData"
            @addUserEditDialogClose="addUserEditDialogClose"
            @addUserEditDialogNoSubmit="
            addUserEditDialogNoSubmit"
        >
        </addUser>
      </el-dialog>

    </el-card>

  </div>
</template>

<script>

import {mapState} from "vuex";
import {serverIp} from "../../../public/config";
import addUser from "@/views/UserManage/addUser";

export default {
  components: {addUser},
  name: "User",
  data() {
    return {
      serverIp: serverIp,
      form: {
        id: "",
      },
      addUserTitle: "",
      //用户选择列表
      userList: [],
      addUserData:{},
      //展示是否在加载中
      loading: false,
      tableData: null,
      pageNum: 1,
      pageSize: 10,
      page: null,
      //记录总数
      total: 0,
      //新增用户隐藏窗口
      addUserVisible : false,
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
      judgeRole : null,
    }
  },
  computed: {
    ...mapState(["componentSize", "componentBtnSize"]),
  },
  created() {
    this.queryAllList()
    this.getSelectRole()
    this.judgeRole  =  localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).role: ""
  },
  methods: {
    queryAllList() {
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        username: this.form.username,
        phone: this.form.phone,
        address: this.form.address,
        email: this.form.email,
        role: this.form.role,
        sex: this.form.sex,
      };
      this.loading = true;
      this.tableData = [];
      this.$axios.queryAllList(params).then(res => {
        console.log(" this.tableData", this.tableData);
        console.log(" this.tableData", res);
        this.tableData = res.beans
        this.total = res.totalCount;
        this.loading = false;
      });
    },
    onSubmit() {
      console.log("onSubmit")
      this.queryAllList()
    },
    reOnSubmit() {
      console.log("reOnSubmit")
      this.pageNum = 1;
      this.pageSize = 10;
      this.form = {};
      this.page = null;
      this.queryAllList()
    },
    tableRowClassName({rowIndex}) {
      if ((rowIndex + 1) % 2 === 0) {
        return "double";
      } else {
        return "single";
      }
    },
    //单选框当前行文件
    handleSelectionChange(rows) {
      this.userList = rows;
    },
    addUser(){
      this.addUserData = {},
      this.addUserTitle = "新增用户";
      this.addUserVisible = true;
    },
    deleteAll() {
      console.log(this.userList)
      if (this.userList == null || this.userList.length == 0) {
        this.$notify({
          title: "警告",
          message: "请至少选择一个用户进行删除",
          type: "warning",
          duration: 1000,
        });
      }else {
        this.$axios.deleteAllUser(this.userList).then((res) => {
          if (res.returnCode == "200") {
            this.$notify({
              title: "成功",
              message: "删除成功",
              type: "success",
              duration: 1000,
            });
            this.onSubmit();
          } else {
            this.$notify.error({
              title: "错误",
              message: "删除失败",
              duration: 1000,
            });
          }
        });
      }
    },
    uploadFile() {
    },
    downloadFile() {
      window.open(`http://${serverIp}:9090/user/export`)
    },
    //选择一页显示多少行数据
    handleSizeChange(val) {
      this.pageSize = val;
      this.queryAllList()
    },
    //显示第几页
    pageChange(val) {
      this.pageNum = val;
      this.queryAllList()
    },
    modifyUser(val) {
      this.addUserTitle = "修改用户";
      console.log(val.row);
      this.addUserData = JSON.parse(JSON.stringify(val.row))
      console.log(this.addUserData);
      this.addUserVisible = true;
    },
    resetPassword(val){
      console.log(val.row)
      this.$axios.resetPassword(val.row).then((res) => {
        if (res.returnCode == "200") {
          this.$notify({
            title: "成功",
            message: "密码重置成功",
            type: "success",
            duration: 1000,
          });
          this.onSubmit();
        } else {
          this.$notify.error({
            title: "错误",
            message: "重置失败",
            duration: 1000,
          });
        }
      });
    },
    deleteUser(val) {
      console.log()
        if (val.row.id != null && val.row.id != "") {
          this.$axios.deleteUser(val.row).then((res) => {
            if (res.returnCode == "200") {
              this.$notify({
                title: "成功",
                message: "删除成功",
                type: "success",
                duration: 1000,
              });
              this.onSubmit();
            } else {
              this.$notify.error({
                title: "错误",
                message: "删除失败",
                duration: 1000,
              });
            }
          });
        } else {
          this.$notify({
            title: "错误",
            message: "该用户不存在",
            type: "warning",
            duration: 1000,
          });
        }
    },
    //关闭编辑弹窗回调
    addUserEditDialogClose() {
      this.addUserVisible = false;
      this.onSubmit();
    },
    //关闭文档编辑弹窗不提交
    addUserEditDialogNoSubmit() {
      this.addUserVisible = false;
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功")
      this.reOnSubmit();
    },
    getSelectRole() {
      this.$axios.getSelectRole().then((res) => {
        console.log(res)
        this.selectRole = res.beans;
      });
    }
  },

}
</script>


<style lang="scss" scoped>
.headerBg {
  background: #eee !important;
}
</style>
