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
        <span class="content-Breadcrumb">角色管理</span>
      </div>

      <el-row>
        <el-col :span="24">
          <el-form
              :inline="true"
              :model="form"
              class="select-no-form"
              label-width="100px"
          >

            <el-form-item label="角色名称" prop="name">
              <el-input v-model="form.name" clearable filterable allow-create :size="componentSize"
                        placeholder="请输入角色名称"></el-input>
            </el-form-item>

            <el-form-item label="角色描述" prop="description">
              <el-input v-model="form.description" clearable filterable allow-create :size="componentSize"
                        placeholder="请输入角色描述"></el-input>
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
              @click="addRole"
          >新增角色
          </el-button>
          <el-button
              type="danger"
              :size="componentBtnSize"
              @click="deleteAll"
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
<!--            <el-table-column prop="id" label="ID" min-width="180 "  align="center"></el-table-column>-->
            <el-table-column
                prop="name"
                label="角色名称"
                width="calc(15%)"
                min-width="200px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="description"
                label="角色描述"
                width="calc(15%)"
                min-width="200px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                label="操作"
                fixed="right"
                align="center"
                width="260"
            >

              <template slot-scope="scope">
                <el-col :span="24">
                  <div>
                    <el-button
                        type="success"
                        style="margin: 0 10px 0px 0"
                        :size="componentBtnSize"
                        @click="addMenu(scope.row)"
                    >分配菜单
                    </el-button>
                    <el-button
                        type="primary"
                        style="margin: 0 10px 0px 0"
                        :size="componentBtnSize"
                        @click="modifyRole(scope)"
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
          :visible.sync="rightVisible"
          :modal-append-to-body='false'
          append-to-body
          width="800px">
        <el-form
            :inline="true"
            :model="addForm"
            label-width="120px"
            label-position="right"
            ref="form"
            class="enter-from-submit"
            v-loading="loading"
        >

          <el-form-item label="用户名称" prop="name">
            <el-input v-model="addForm.name" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入角色名称"></el-input>
          </el-form-item>

          <el-form-item label="角色描述" prop="description">
            <el-input v-model="addForm.description" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入角色描述"></el-input>
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
      <el-dialog
          :title="menuTitle"
          :close-on-click-modal='false'
          :visible.sync="menuVisible"
          :modal-append-to-body='false'
          append-to-body
          width="600px">
        <el-form
            :inline="true"
            :model="addForm"
            label-width="120px"
            label-position="right"
            ref="form"
            class="enter-from-submit"
            v-loading="loading"
        >
          <el-tree
              :data="menuData"
              :props="defaultProps"
              show-checkbox
              node-key="id"
              ref="tree"
              :default-expanded-keys="expends"
              :default-checked-keys="checks"
              style="font-size: 16px"
              :check-change="handleCheckChange">
            <span slot-scope="{node,data}">
              <span><i :class="data.icon"/> {{data.name}}</span>
            </span>
          </el-tree>

          <el-row>
            <el-col :span="5">
              <el-form-item>
                <el-button type="primary"  :size="componentBtnSize" @click="insertRoleMenu">{{ submitName }}</el-button>
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

export default {
  name: "User",
  data() {
    return {
      form: {
        id: "",
      },
      addForm: {
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
      submitName: "确定",
      roleList: [],
      menuTitle :"分配菜单",
      menuVisible:false,

      //权限树
      menuData: null,
      defaultProps: {
        id:"id",
        label: "name",
        children: "children",
      },
      expends:[],
      checks: [],
      roleId: 0,
      roleFlag: '',
      ids: []
    }
  },
  computed: {
    ...mapState(["componentSize", "componentBtnSize"]),
  },
  created() {
    this.queryAllRoleList()
  },
  methods: {
    handleCheckChange(){
      console.log("handleCheckChange方法")
    },
    getMenuTree(roleId) {
      console.log(roleId)
      this.$axios.getMenuTree().then((res) => {
        console.log(res.beans)
        //获取菜单树
        this.menuData = res.beans;
        //获取权限
        this.expends = this.menuData.map(v => v.id);
      });

      let params = {
        roleId: this.roleId,
      };

      this.$axios.getRoleMenu(params).then((res) => {
        this.checks = res.beans
        this.ids.forEach(id => {
          if (!this.checks.includes(id)) {
            this.$nextTick(() => {
              this.$refs.tree.setChecked(id, false)
            })
          }
        })
      });


    },
    //选择一页显示多少行数据
    handleSizeChange(val) {
      this.pageSize = val;
      this.queryAllRoleList()
    },
    queryAllRoleList() {
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.form.name,
        description: this.form.description,
      };
      this.loading = true;
      this.tableData = [];
      this.$axios.queryAllRoleList(params).then(res => {
        this.tableData = res.beans
        this.total = res.totalCount;
        this.loading = false;
      });
    },
    //显示第几页
    pageChange(val) {
      this.pageNum = val;
      this.queryAllRoleList()
    },
    insertRoleMenu(){
      let params = {
        roleId: this.roleId,
        menuIds: this.$refs.tree.getCheckedKeys(),
      };
      console.log(params)
      this.$axios.insertRoleMenu(params).then(res => {
        console.log(res)
        if (res.returnCode == "200") {
          this.$notify({
            title: "成功",
            message: "操作成功",
            type: "success",
            duration: 1000,
          });
          this.menuVisible = false;
        } else {
          this.$notify.error({
            title: "错误",
            message: "操作失败",
            duration: 1000,
          });
        }
      });
      console.log("确定角色权限菜单")
    },
    addMenu(role){
      this.roleId = role.id
      this.menuVisible = true;
      this.getMenuTree(this.roleId);
    },
    addRole() {
      this.title = "新增角色";
      this.addForm = {};
      this.rightVisible = true;
    },
    deleteAll() {
      console.log(this.roleList)
      if (this.roleList == null || this.roleList.length == 0) {
        this.$notify({
          title: "警告",
          message: "请至少选择一条数据进行删除",
          type: "warning",
          duration: 1000,
        });
      } else {
        this.$confirm('确定删除这些数据吗？', '提示', {type: 'warning'}).then(() => {
          this.$axios.deleteAllRole(this.roleList).then(res => {
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
    modifyRole(val) {
      this.addForm = JSON.parse(JSON.stringify(val.row))
      this.title = "修改角色";
      this.rightVisible = true;
    },
    insertSubmit() {
      if (this.title == "新增角色") {
        this.$axios.addRole(this.addForm).then(res => {
          if (res.returnCode == "200") {
            this.addRoleEditDialogClose();
            this.$notify({
              title: "成功",
              message: "新增成功",
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
      } else {
        this.$axios.addRole(this.addForm).then(res => {
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
      }

    },
    deleteRole(val) {
      if (val.row.id != null && val.row.id != "") {
        this.$axios.deleteRole(val.row).then((res) => {
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
      this.roleList = rows;
    },
    onSubmit() {
      console.log("onSubmit")
      this.queryAllRoleList()
    },
    reOnSubmit() {
      console.log("reOnSubmit")
      this.pageNum = 1;
      this.pageSize = 10;
      this.form = {};
      this.page = null;
      this.queryAllRoleList()
    },
    //关闭编辑弹窗回调
    addRoleEditDialogClose() {
      this.rightVisible = false;
      this.onSubmit();
    },
    //关闭文档编辑弹窗不提交
    addRoleEditDialogNoSubmit() {
      this.rightVisible = false;
    },

  }
}
</script>


<style>
.headerBg {
  background: #eee !important;
}
</style>
