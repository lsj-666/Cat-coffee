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
        <span class="content-Breadcrumb">菜单管理</span>
      </div>

      <el-row :gutter="20">
        <el-col
            :span="1"
            class="tab-left-col"
        >
          <el-card
              class="box-card"
              shadow="hover"
          >

            <el-input
                placeholder="输入关键字进行过滤"
                v-model="filterText"
                clearable>
            </el-input>
            <el-tree
                :data="treeData"
                :props="props"
                :highlight-current="true"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                @node-click="handleNodeClick"
                :default-expand-all="true"
                ref="tree"
            >
            </el-tree>
          </el-card>
        </el-col>
        <el-col span:1 class="tab-right-col">

          <el-row>
            <el-col :span="24">
              <el-form
                  :inline="true"
                  :model="form"
                  class="select-no-form"
                  label-width="100px"
              >

                <el-form-item label="菜单名称" prop="name">
                  <el-input v-model="form.name" clearable filterable allow-create :size="componentSize"
                            placeholder="请输入菜单名称"></el-input>
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
          <el-form
              :inline="true"
              class="select-no-form"
              label-width="70px"
          >
            <el-button
                type="primary"
                style="margin: 0 0px 10px 0"
                :size="componentBtnSize"
                @click="addMenu"
            >新增菜单
            </el-button>
            <el-button
                type="danger"
                :size="componentBtnSize"
                @click="deleteAll"
            >批量删除
            </el-button>
            <div style="font-size: 15px;font-weight: bold;margin-bottom: 5px">
              {{ breadTitle }}
            </div>
            <el-table
                :data="tableData"
                v-loading="loading"
                ref="tableData"
                border
                row-key="id"
                default-expand-all
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
                  label="菜单名称"
                  width="calc(15%)"
                  min-width="150px"
                  align="center"
                  show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                  prop="path"
                  label="菜单路径"
                  width="calc(15%)"
                  min-width="260px"
                  align="center"
                  show-overflow-tooltip
              >
              </el-table-column>

              <el-table-column
                  label="菜单图标"
                  width="calc(15%)"
                  min-width="100px"
                  align="center"
                  show-overflow-tooltip

              >
                <template slot-scope="scope">
                  <i :class="scope.row.icon" style="font-size: 24px"/>
                </template>
              </el-table-column>

              <el-table-column
                  prop="pagePath"
                  label="页面路径"
                  width="calc(15%)"
                  min-width="260px"
                  align="center"
                  show-overflow-tooltip
              >
              </el-table-column>

              <el-table-column
                  prop="description"
                  label="菜单描述"
                  width="calc(15%)"
                  min-width="260px"
                  align="center"
                  show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                  prop="sort"
                  label="菜单排序"
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
                    <span @click="modifyMenu(scope)">
                      <template>
                        <span style="color: #409eff; cursor: pointer;margin-right: 12px">编辑</span>
                      </template>
                    </span>
                      <el-popconfirm title="确定删除该条数据吗？" @confirm="deleteMenu(scope)">
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
      </el-row>



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
            ref="addForm"
            class="enter-from-submit"
            v-loading="loading"
        >

          <el-form-item label="菜单名称" prop="name">
            <el-input v-model="addForm.name" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入菜单名称"></el-input>
          </el-form-item>

          <el-form-item label="菜单路径" prop="path">
            <el-input v-model="addForm.path" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入菜单名称"></el-input>
          </el-form-item>

          <el-form-item label="菜单描述" prop="description">
            <el-input v-model="addForm.description" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入菜单描述"></el-input>
          </el-form-item>

          <el-form-item label="页面路径" prop="pagePath">
            <el-input v-model="addForm.pagePath" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入页面路径"></el-input>
          </el-form-item>

          <el-form-item label="菜单排序" prop="sort">
            <el-input v-model="addForm.sort" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入菜单排序"></el-input>
          </el-form-item>

          <el-form-item label="菜单图标" prop="icon">
            <el-select v-model="addForm.icon" :size="componentSize" filterable clearable placeholder="请选择菜单图标">
              <el-option
                  v-for="(item,index) in selectIcon"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
              >
                <i :class="item.value" />{{item.name}}
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="父目录" prop="pid">
            <el-select v-model="addForm.pid" :size="componentSize"  clearable placeholder="请选择菜单父目录">
              <el-option
                  v-for="(item,index) in selectPid"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
              >
              </el-option>
            </el-select>
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
                <el-button @click="addMenuEditDialogNoSubmit">取消</el-button>
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
  watch: {
    //关键字过滤
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  data() {
    return {
      //菜单父目录下拉框
      selectPid: [],
      //图标下拉框
      selectIcon: [],
      form: {
        id: "",
        icon:"",
      },
      addForm: {
        id: "",
        icon:"",
      },
      //面包屑组件保存路径
      breadTitle: "",
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
      menuList: [],
      filterText: '',
      treeData: null,
      props: {
        label: "name",
        children: "children",
      },
      pagePath:"",
      sort:"",

    }
  },
  computed: {
    ...mapState(["componentSize", "componentBtnSize"]),
  },
  created() {
    this.queryAllMenuList()
    this.getMenuTree()
  },
  methods: {
    //获取父目录选项下拉框
    getPidSelect(val) {
      console.log("获取父目录下拉框的id"+val.id);
      this.$axios.getPidSelect(val).then((res) => {
        this.selectPid = res.beans;
      });
    },
    //选择一页显示多少行数据
    handleSizeChange(val) {
      this.pageSize = val;
      this.queryAllMenuList()
    },
    queryAllMenuList() {
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.form.name,
        description: this.form.description,
        pagePath: this.form.pagePath,
        sort: this.form.sort,
        id : this.id,
      };
      this.loading = true;
      this.tableData = [];
      this.$axios.queryAllMenuList(params).then(res => {
        this.tableData = res.beans
        this.total = res.totalCount;
        this.loading = false;
      });
    },
    //显示第几页
    pageChange(val) {
      this.pageNum = val;
      this.queryAllMenuList()
    },
    addMenu() {
      this.title = "新增菜单";
      this.addForm = {};
      this.rightVisible = true;
      this.getPidSelect(this.addForm);
      this.getIcon();
    },
    deleteAll() {
      console.log(this.menuList)
      if (this.menuList == null || this.menuList.length == 0) {
        this.$notify({
          title: "警告",
          message: "请至少选择一条数据进行删除",
          type: "warning",
          duration: 1000,
        });
      } else {
        this.$confirm('确定删除这些数据吗？', '提示', {type: 'warning'}).then(() => {
          this.$axios.deleteAllMenu(this.menuList).then(res => {
            if (res.returnCode == "200") {
              this.$notify({
                title: "成功",
                message: "删除成功",
                type: "success",
                duration: 1000,
              });
              this.reOnSubmit();
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
    modifyMenu(val) {
      this.addForm = JSON.parse(JSON.stringify(val.row))
      this.title = "修改菜单";
      this.rightVisible = true;
      this.getPidSelect(this.addForm);
      this.getIcon()
    },
    getIcon(){
      this.$axios.getIconSelect().then((res) => {
        this.selectIcon = res.beans;
      });
    },
    insertSubmit() {
      if (this.title == "新增菜单") {
        this.$axios.addMenu(this.addForm).then(res => {
          if (res.returnCode == "200") {
            this.addMenuEditDialogClose();
            this.$notify({
              title: "成功",
              message: "新增成功",
              type: "success",
              duration: 1000,
            });
            this.reOnSubmit()
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
        this.$axios.addMenu(this.addForm).then(res => {
          if (res.returnCode == "200") {
            this.addMenuEditDialogClose();
            this.$notify({
              title: "成功",
              message: "修改成功",
              type: "success",
              duration: 1000,
            });
            this.reOnSubmit()
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
    deleteMenu(val) {
      if (val.row.id != null && val.row.id != "") {
        this.$axios.deleteMenu(val.row).then((res) => {
          if (res.returnCode == "200") {
            this.$notify({
              title: "成功",
              message: "删除成功",
              type: "success",
              duration: 1000,
            });
            this.reOnSubmit();
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
    //关键字搜索
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    handleNodeClick(val, e){
      this.loading = true;
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        id: val.id,
      };
      this.tableData = [];
      this.id = val.id;
      this.name = val.name;
      this.form.name = "";
      this.$axios.queryAllMenuList(params).then(res => {
        this.tableData = res.beans
        this.total = res.totalCount;
        this.loading = false;
      });
      // 显示当前点击的目录路径
      this.breadTitle = ""
      if (val.pid != '-1') {
        do {
          this.breadTitle = " / " + val.name + this.breadTitle;
          val = e.parent.data;
          e = e.parent
        } while (val.pid != '-1')//判断该节点是否为根目录的子目录
        this.breadTitle = val.name + this.breadTitle;
      } else {
        this.breadTitle = val.name + this.breadTitle;
      }
    },
    //获取菜单树
    getMenuTree() {
      console.log("这里在获取菜单树")
      this.$axios.getMenuTree().then((res) => {
        console.log(res.beans)
        this.treeData = res.beans;
      });
    },
    //单选框当前行文件
    handleSelectionChange(rows) {
      this.menuList = rows;
    },
    onSubmit() {
      console.log("onSubmit")
      this.queryAllMenuList()
    },
    reOnSubmit() {
      console.log("reOnSubmit")
      this.pageNum = 1;
      this.pageSize = 10;
      this.form = {};
      this.id = "";
      this.page = null;
      this.breadTitle = "";
      this.queryAllMenuList();
      this.getMenuTree();
    },
    //关闭编辑弹窗回调
    addMenuEditDialogClose() {
      this.rightVisible = false;
      this.onSubmit();
    },
    //关闭文档编辑弹窗不提交
    addMenuEditDialogNoSubmit() {
      this.rightVisible = false;
    },

  }
}
</script>


<style lang="scss" scoped>
.headerBg {
  background: #eee !important;
}

.tab-left-col {
  width: 300px;

  .box-card {
    overflow: auto;
    min-height: 500px;
    max-height: 700px;
  }

}

.tab-right-col {
  width: calc(100% - 300px);
}
</style>
