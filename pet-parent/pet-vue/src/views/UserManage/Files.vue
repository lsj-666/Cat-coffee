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
        <span class="content-Breadcrumb">文件管理</span>
      </div>

      <el-row>
        <el-col :span="24">
          <el-form
              :inline="true"
              :model="form"
              class="select-no-form"
              label-width="100px"
          >

            <el-form-item label="文件名称" prop="name">
              <el-input v-model="form.name" clearable filterable allow-create :size="componentSize"
                        placeholder="请输入文件名称"></el-input>
            </el-form-item>

            <el-form-item label="文件类型" prop="type">
              <el-select v-model="form.type" :size="componentSize"  clearable placeholder="请选择文件类型">
                <el-option
                    v-for="(item,index) in selectType"
                    :key="index"
                    :label="item.type"
                    :value="item.type"
                >
                </el-option>
              </el-select>
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
              type="success"
              style="margin: 0 0px 10px 0"
              :size="componentBtnSize"
              v-if="judgeRole == 1 || judgeRole == 2"

          >
            <template>
              <el-upload
                  class="avatar-uploader"
                  :action="'http://' + serverIp +':9090/file/uploadPage'"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  style="max-width: 300px"
              >
                <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar" style="max-width: 200px;max-height: 200px">
                <i v-else class="el-icon-plus avatar-uploader-icon" style="max-height: 100px">新增文件</i>
              </el-upload>
            </template>
          </el-button>
          <el-button
              type="primary"
              style="margin: 0 0px 10px 10px"
              :size="componentBtnSize"
              @click="downloadAll"
          >批量下载文件
          </el-button>
          <el-button
              type="danger"
              :size="componentBtnSize"
              @click="deleteAll"
              v-if="judgeRole == 1 || judgeRole == 2"
          >批量删除
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
                prop="name"
                label="文件名称"
                width="calc(15%)"
                min-width="100px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="type"
                label="文件类型"
                width="calc(15%)"
                min-width="100px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="url"
                label="文件url"
                width="calc(15%)"
                min-width="260px"
                align="center"
                show-overflow-tooltip
            >
              <template slot-scope="scope">
                     <span
                         style="color: #409eff; cursor: pointer"
                         @click="downloadItem(scope.row)"
                     >{{ scope.row.url }}</span>
              </template>
            </el-table-column>
            <el-table-column
                label="操作"
                fixed="right"
                align="center"
                width="260"
                v-if="judgeRole == 1 || judgeRole == 2"
            >

              <template slot-scope="scope">
                <el-col :span="24">
                  <div>
                    <el-button
                        type="primary"
                        style="margin: 0 10px 0px 0"
                        :size="componentBtnSize"
                        @click="modifyFile(scope)"
                    >编辑
                    </el-button>
                    <el-popconfirm title="确定删除该条数据吗？" @confirm="deleteRole(scope)">
                      <el-button
                          type="danger"
                          :size="componentBtnSize"
                          slot="reference"
                          class="assets-close-Ass"
                          style="
                          margin: 0 10px 0px 0;
                  word-break: break-all;
                  text-overflow: ellipsis;
                  overflow: hidden;
                  white-space: nowrap; cursor: pointer;
                "
                      >删除
                      </el-button>
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
          :title="title"
          :close-on-click-modal='false'
          :visible.sync="modifyrightVisible"
          :modal-append-to-body='false'
          append-to-body
          width="800px">
        <el-form
            :inline="true"
            :model="modifyForm"
            label-width="120px"
            label-position="right"
            ref="form"
            class="enter-from-submit"
            v-loading="loading"
        >

          <el-form-item label="文件名称" prop="name">
            <el-input v-model="modifyForm.name" clearable filterable allow-create :size="componentSize" style="min-width: 350px"
                      placeholder="请输入文件名称"></el-input>
          </el-form-item>

          <el-row>
            <el-col :span="10">
              <el-form-item>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-button type="primary" @click="insertSubmit('form')">{{ submitName }}</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-button @click="addRoleEditDialogNoSubmit">取消</el-button>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>

      </el-dialog>

    </el-card>
  </div>
</template>

<script>
import {mapState} from "vuex";
import {serverIp} from "../../../public/config";

export default {
  name: "User",
  data() {
    return {
      form: {
        id: "",
      },
      serverIp: serverIp,
      selectType:[],
      addForm: {
        id: "",
      },
      modifyForm: {
        id: "",
      },
      //展示是否在加载中
      loading: false,
      tableData: null,
      pageNum: 1,
      pageSize: 10,
      page: null,
      //记录总数
      total: 0,
      title: "",
      rightVisible: false,
      modifyrightVisible: false,
      submitName: "确定",
      fileList: [],
      menuTitle: "分配菜单",
      menuVisible: false,

      //权限树
      menuData: null,
      defaultProps: {
        id: "id",
        label: "name",
        children: "children",
      },
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag: '',
      ids: [],
      allFileList: [],
      judgeRole:null,
    }
  },
  computed: {
    ...mapState(["componentSize", "componentBtnSize"]),
  },
  created() {
    this.queryAllFileList()
    this.selectFileType()
    this.judgeRole  =  localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).role: ""
  },
  methods: {
    selectFileType(){
      this.$axios.selectFileType().then(res => {
        this.selectType = res.beans;
      })
    },
    downloadItem(val) {
      window.location.href = val.url
      // this.$axios.downloadItem(val.url).then(res => {
      //   const blob = new Blob([res]); //处理文档流
      //   const fileName = this.form.name ;
      //   const elink = document.createElement("a");
      //   elink.download = fileName;
      //   elink.style.display = "none";
      //   elink.href = URL.createObjectURL(blob);
      //   document.body.appendChild(elink);
      //   elink.click();
      //   URL.revokeObjectURL(elink.href); // 释放URL 对象
      //   document.body.removeChild(elink);
      // })
    },
    downloadAll() {
      if (this.allFileList.length == 0 || this.allFileList == null || this.allFileList == '') {
        this.$message.warning({
          title: "警告",
          message: "请选择至少一篇文档进行下载",
          duration: 1000,
        });
        return
      }
      this.$axios.downloadAll(this.allFileList).then(res => {
        if (res.returnCode == '200') {
          window.location.href = res.object
        } else {
          this.$message({
            title: "失败",
            message: "下载失败",
            type: "danger",
            duration: 1000,
          });
        }

        // const blob = new Blob([res]); //处理文档流
        // const fileName = this.dirName + ".zip";
        // const elink = document.createElement("a");
        // elink.download = fileName;
        // elink.style.display = "none";
        // elink.href = URL.createObjectURL(blob);
        // document.body.appendChild(elink);
        // elink.click();
        // URL.revokeObjectURL(elink.href); // 释放URL 对象
        // document.body.removeChild(elink);
      });
    },
    handleCheckChange() {
      console.log("handleCheckChange方法")
    },
    //选择一页显示多少行数据
    handleSizeChange(val) {
      this.pageSize = val;
      this.queryAllFileList()
    },
    queryAllFileList() {
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.form.name,
        type: this.form.type,
      };
      this.loading = true;
      this.tableData = [];
      this.$axios.queryAllFileList(params).then(res => {
        this.tableData = res.beans
        this.total = res.totalCount;
        this.loading = false;
      });
    },
    //显示第几页
    pageChange(val) {
      this.pageNum = val;
      this.queryAllFileList()
    },
    deleteAll() {
      console.log(this.fileList)
      if (this.fileList == null || this.fileList.length == 0) {
        this.$notify({
          title: "警告",
          message: "请至少选择一条数据进行删除",
          type: "warning",
          duration: 1000,
        });
      } else {
        this.$confirm('确定删除这些数据吗？', '提示', {type: 'warning'}).then(() => {
          this.$axios.deleteAllFile(this.fileList).then(res => {
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
        });

      }
    },
    modifyFile(val) {
      this.modifyForm = JSON.parse(JSON.stringify(val.row))
      this.title = "修改文件";
      this.modifyrightVisible = true;
    },
    handleAvatarSuccess(res) {
      this.$message({
        title: "成功",
        message: "上传成功",
        type: "success",
        duration: 1000,
      });
      this.queryAllFileList()
    },
    insertSubmit() {
        this.$axios.modifyFile(this.modifyForm).then(res => {
          if (res.returnCode == "200") {
            this.addRoleEditDialogClose();
            this.$notify({
              title: "成功",
              message: "修改成功",
              type: "success",
              duration: 1000,
            });
          } else {
            this.$notify({
              title: "错误",
              message: res.returnMessage,
              type: "warning",
              duration: 1000,
            });
          }
        });
    },
    deleteRole(val) {
      if (val.row.id != null && val.row.id != "") {
        this.$axios.deleteFile(val.row).then((res) => {
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
          message: "角色异常！",
          type: "warning",
          duration: 1000,
        });
      }
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
      this.fileList = rows;
      this.allFileList = rows;
      console.log("1111111111" + rows)
      console.log("2222222222", rows[0].url)
    },
    onSubmit() {
      console.log("onSubmit")
      this.queryAllFileList()
    },
    reOnSubmit() {
      console.log("reOnSubmit")
      this.pageNum = 1;
      this.pageSize = 10;
      this.form = {};
      this.page = null;
      this.queryAllFileList()
    },
    //关闭编辑弹窗回调
    addRoleEditDialogClose() {
      this.rightVisible = false;
      this.modifyrightVisible = false;
      this.onSubmit();
    },
    //关闭文档编辑弹窗不提交
    addRoleEditDialogNoSubmit() {
      this.rightVisible = false;
      this.modifyrightVisible = false;
    },

  }
}
</script>


<style>
.headerBg {
  background: #eee !important;
}
</style>
