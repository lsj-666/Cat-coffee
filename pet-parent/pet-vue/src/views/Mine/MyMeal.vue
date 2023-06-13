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
        <span class="content-Breadcrumb">预约管理</span>
      </div>

      <el-row>
        <el-col :span="24">
          <el-form
              :inline="true"
              :model="form"
              class="select-no-form"
              label-width="100px"
          >
<!--            <el-form-item label="预约人" prop="userId">-->
<!--              <el-select v-model="form.userId" :size="componentSize" filterable clearable placeholder="请选择预约人">-->
<!--                <el-option-->
<!--                    v-for="(item,index) in selectMaster"-->
<!--                    :key="index"-->
<!--                    :label="item.username"-->
<!--                    :value="item.id"-->
<!--                >-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </el-form-item>-->

            <el-form-item label="预约套餐" prop="meal">
              <el-select v-model="form.meal" :size="componentSize" filterable clearable placeholder="请选择套餐">
                <el-option
                    v-for="(item,index) in selectMeal"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="预约时间" prop="time">
              <el-date-picker
                  v-model="form.time"
                  type="datetime"
                  placeholder="请选择预约的时间"
                  :size="componentSize"
                  value-format="yyyy-MM-dd HH:mm:ss"
              >
                >
              </el-date-picker>
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
<!--          <el-button-->
<!--              type="primary"-->
<!--              style="margin: 0 0px 10px 0"-->
<!--              :size="componentBtnSize"-->
<!--              @click="addMeal"-->
<!--          >新增预约-->
<!--          </el-button>-->
          <el-button
              type="danger"
              :size="componentBtnSize"
              @click="deleteAll"
          >批量删除
          </el-button>
        </el-form>

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
              label="预约人"
              width="calc(15%)"
              min-width="100px"
              align="center"
              show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
              prop="meal"
              label="预约套餐"
              width="calc(15%)"
              min-width="100px"
              align="center"
              show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span v-if="scope.row.meal == '1'">48元单人不限时撸猫体验券</span>
              <span v-else-if="scope.row.meal == '2'">58元单人不限时撸猫体验券</span>
              <span v-else-if="scope.row.meal == '3'">68元单人不限时撸猫体验券</span>
              <span v-else-if="scope.row.meal == '4'">78元单人不限时撸猫体验券</span>
            </template>
          </el-table-column>
          <el-table-column
              prop="time"
              label="预约到店时间"
              width="calc(15%)"
              min-width="100px"
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
                      type="primary"
                      style="margin: 0 10px 0px 0"
                      :size="componentBtnSize"
                      @click="modifyMeal(scope)"
                  >编辑
                  </el-button>
                  <el-popconfirm title="确定删除该条数据吗？" @confirm="deleteMeal(scope)">
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
            :page-sizes="[10, 20, 30]"
            :page-size="10"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            class="pagination-center"
        >
        </el-pagination>

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
          <el-form-item label="预约人" prop="userId"  v-if="judgeRole == 1 || judgeRole == 2">
            <el-select v-model="addForm.userId" :size="componentSize" filterable clearable placeholder="请选择预约人">
              <el-option
                  v-for="(item,index) in selectMaster"
                  :key="index"
                  :label="item.username"
                  :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="预约套餐" prop="meal">
            <el-select v-model="addForm.meal" :size="componentSize" filterable clearable placeholder="请选择套餐">
              <el-option
                  v-for="(item,index) in selectMeal"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="预约时间" prop="time">
            <el-date-picker
                v-model="addForm.time"
                type="datetime"
                placeholder="请选择预约的时间"
                :size="componentSize"
                value-format="yyyy-MM-dd HH:mm:ss"
            >
              >
            </el-date-picker>
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
                <el-button @click="addMealEditDialogNoSubmit">取消</el-button>
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
  name: "NotFound",
  data(){
    return {
      form: {
      },
      addForm: {
      },
      submitName:"保存",
      judgeRole:null,
      tableData:null,
      mealList:[],
      pageNum: 1,
      pageSize: 10,
      page: null,
      //记录总数
      total: 0,
      title:"",
      loading:false,
      rightVisible:false,
      selectMaster : [],
      selectMeal: [{
        name: '48元单人不限时体验券',
        value: '1',
      },
        {
          name: '58元单人不限时体验券',
          value: '2',
        },{
          name: '68元单人不限时体验券',
          value: '3',
        },{
          name: '78元单人不限时体验券',
          value: '4',
        },
      ],
    }
  },
  computed: {
    ...mapState(["componentSize", "componentBtnSize"]),
  },
  created() {
    this.getSelectMaster()
    this.queryMyMealList();
    this.judgeRole  =  localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).role: ""
  },
  methods:{
    getSelectMaster() {
      this.$axios.selectMaster().then((res) => {
        this.selectMaster = res.beans
      })
    },
    queryMyMealList(){
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        userId:   JSON.parse(localStorage.getItem("user")).id,
        meal: this.form.meal,
        time: this.form.time,
      };
      this.loading = true;
      this.tableData = [];
      console.log("-----"+params)
      this.$axios.queryAllMealList(params).then(res => {
        this.tableData = res.beans
        this.total = res.totalCount;
        this.loading = false;
      });
    },
    modifyMeal(val){
      console.log(val)
      this.addForm = JSON.parse(JSON.stringify(val.row));
      this.title = '修改预约信息'
      this.rightVisible = true;
    },
    deleteMeal(val){
      if (val.row.id != null && val.row.id != "") {
        this.$axios.delMealList(val.row).then((res) => {
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
          message: "信息异常！",
          type: "warning",
          duration: 1000,
        });
      }
    },
    addMeal(){
      this.addForm = {},
          this.title = '新增预约信息'
      this.rightVisible = true;
    },
    deleteAll(){
      if (this.mealList == null || this.mealList.length == 0) {
        this.$notify({
          title: "警告",
          message: "请至少选择一条数据进行删除",
          type: "warning",
          duration: 1000,
        });
      } else {
        this.$confirm('确定删除这些数据吗？', '提示', {type: 'warning'}).then(() => {
          this.$axios.delAllMeal(this.mealList).then(res => {
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
    onSubmit(){
      this.queryMyMealList()
    },
    reOnSubmit(){
      console.log("rererere")
      this.pageNum = 1;
      this.pageSize = 10;
      this.form = {};
      this.page = null;
      this.queryMyMealList()
    },
    //单选框当前行
    handleSelectionChange(rows) {
      this.mealList = rows;
    },
    tableRowClassName({rowIndex}) {
      if ((rowIndex + 1) % 2 === 0) {
        return "double";
      } else {
        return "single";
      }
    },
    //选择一页显示多少行数据
    handleSizeChange(val) {
      this.pageSize = val;
      this.queryMyMealList()
    },
    //显示第几页
    pageChange(val) {
      this.pageNum = val;
      this.queryMyMealList()
    },
    //关闭编辑弹窗回调
    addMealEditDialogClose() {
      this.rightVisible = false;
      this.onSubmit();
    },
    insertSubmit(){
      if (this.title == "新增预约信息") {
        this.$axios.addMealList(this.addForm).then(res => {
          if (res.returnCode == "200") {
            this.addMealEditDialogClose();
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
        this.$axios.addMealList(this.addForm).then(res => {
          if (res.returnCode == "200") {
            this.addMealEditDialogClose();
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
    //关闭文档编辑弹窗不提交
    addMealEditDialogNoSubmit() {
      this.rightVisible = false;
    },
    formatter (inputTime) {
      if(!inputTime && typeof inputTime !== 'number'){
        return '';
      }
      var localTime = '';
      inputTime = new Date(inputTime).getTime();
      const offset = (new Date()).getTimezoneOffset();
      localTime = (new Date(inputTime - offset * 60000)).toISOString();
      localTime = localTime.substr(0, localTime.lastIndexOf('.'));
      localTime = localTime.replace('T', ' ');
      return localTime;
    },
  }
}
</script>

<style>
</style>
