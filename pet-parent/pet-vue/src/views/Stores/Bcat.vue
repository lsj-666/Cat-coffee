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
        <span class="content-Breadcrumb">外地喵</span>
      </div>

      <el-row>
        <el-col :span="24">
          <el-form
              :inline="true"
              :model="form"
              class="select-no-form"
              label-width="100px"
          >
            <el-form-item label="宠物姓名" prop="name">
              <el-input v-model="form.name" clearable filterable allow-create :size="componentSize"
                        placeholder="请输入宠物姓名"></el-input>
            </el-form-item>

            <el-form-item label="姓别" prop="sex">
              <el-select v-model="form.sex" :size="componentSize" clearable placeholder="请选择姓别">
                <el-option
                    v-for="(item,index) in selectSex"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="是否上岗" prop="isWork">
              <el-select v-model="form.isWork" :size="componentSize" clearable placeholder="请选择是否上岗">
                <el-option
                    v-for="(item,index) in selectisWork"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="铲屎官" prop="master">
              <el-select v-model="form.master" :size="componentSize" filterable clearable placeholder="请选择铲屎官">
                <el-option
                    v-for="(item,index) in selectMaster"
                    :key="index"
                    :label="item.username"
                    :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="多联疫苗" prop="vaccine1">
              <el-select v-model="form.vaccine1" :size="componentSize" clearable placeholder="请选择已接种多联疫苗针数">
                <el-option
                    v-for="(item,index) in selectVaccine1"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="狂犬疫苗" prop="vaccine2">
              <el-select v-model="form.vaccine2" :size="componentSize" clearable placeholder="请选择是否接种狂犬疫苗">
                <el-option
                    v-for="(item,index) in selectVaccine2"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="健康情况" prop="health">
              <el-select v-model="form.health" :size="componentSize" clearable placeholder="请选择健康状况">
                <el-option
                    v-for="(item,index) in selectHealth"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" :size="componentSize" clearable placeholder="请选择宠物状态">
                <el-option
                    v-for="(item,index) in selectStatus"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
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
              type="primary"
              style="margin: 0 0px 10px 0"
              :size="componentBtnSize"
              @click="addCcat"
              v-if="judgeRole == 1 || judgeRole == 2"
          >新增宠物
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
                prop="picture"
                label="头像"
                width="calc(15%)"
                min-width="100px"
                min-height="100px"
                align="center"
                show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-popover placement="right" title="" trigger="hover">

                  <img slot="reference" :src="scope.row.picture" style="max-height: 100px;max-width: 100px"/>

                  <!--                                  <img slot="reference" :src="scope.row.picture" :alt="scope.row.picture" style="max-height: 50px;max-width: 130px">-->
                </el-popover>
              </template>

            </el-table-column>
            <el-table-column
                prop="name"
                label="宠物名称"
                width="calc(15%)"
                min-width="100px"
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
                <span v-if="scope.row.sex == '1'">小公猫</span>
                <span v-else-if="scope.row.sex == '0'">小母猫</span>
              </template>

            </el-table-column>
            <el-table-column
                prop="age"
                label="年龄"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="habit"
                label="性格"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="hobby"
                label="爱好"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="isWork"
                label="是否上岗"
                width="calc(15%)"
                min-width="80px"
                align="center"
                show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span v-if="scope.row.isWork == '1'">上岗</span>
                <span v-else-if="scope.row.isWork == '0'">摆烂</span>
              </template>
            </el-table-column>
            <el-table-column
                prop="username"
                label="铲屎官"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="vaccine1"
                label="多联疫苗针数"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="vaccine2"
                label="狂犬疫苗"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span v-if="scope.row.vaccine2 == '1'">未接种</span>
                <span v-else-if="scope.row.vaccine2 == '0'">已接种</span>
              </template>
            </el-table-column>
            <el-table-column
                prop="health"
                label="健康情况"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span v-if="scope.row.vaccine2 == '1'">生病</span>
                <span v-else-if="scope.row.vaccine2 == '0'">健康</span>
              </template>
            </el-table-column>
            <el-table-column
                prop="status"
                label="状态"
                width="calc(15%)"
                min-width="150px"
                align="center"
                show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span v-if="scope.row.status == '2'">居家</span>
                <span v-if="scope.row.status == '1'" style="color: #409eff; cursor: pointer"
                      @click="pageToDcat">流浪</span>
                <span v-else-if="scope.row.status == '0'">寄养</span>
                <span v-else-if="scope.row.status == '-1'">原住民</span>
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
                        @click="modifyCcat(scope)"
                    >编辑
                    </el-button>
                    <el-popconfirm title="确定送去打工吗？" @confirm="changeToWork(scope)">
                      <el-button
                          type="success"
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
                      >派去打工
                      </el-button>
                    </el-popconfirm>
                    <el-popconfirm title="确定删除该条数据吗？" @confirm="deleteCcat(scope)">
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
              :page-sizes="[5, 10, 15]"
              :page-size="5"
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
          <el-row>
            <el-form-item label="头像" prop="picture">
              <el-upload
                  class="avatar-uploader"
                  :action="'http://' + serverIp +':9090/file/upload'"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  style="max-width: 300px;margin-left: 80px"
              >
                <img v-if="this.addForm.picture" :src="this.addForm.picture" class="avatar"
                     style="max-width: 200px;max-height: 200px">
                <i v-else class="el-icon-plus avatar-uploader-icon" style="max-height: 100px">请上传头像</i>
              </el-upload>
            </el-form-item>
          </el-row>


          <el-form-item label="宠物名称" prop="name">
            <el-input v-model="addForm.name" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入宠物名称"></el-input>
          </el-form-item>

          <el-form-item label="姓别" prop="sex">
            <el-select v-model="addForm.sex" :size="componentSize" clearable placeholder="请选择姓别">
              <el-option
                  v-for="(item,index) in selectSex"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="宠物年龄" prop="age">
            <el-input v-model="addForm.age" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入宠物年龄"></el-input>
          </el-form-item>

          <el-form-item label="宠物性格" prop="habit">
            <el-input v-model="addForm.habit" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入宠物性格"></el-input>
          </el-form-item>
          <el-form-item label="宠物爱好" prop="hobby">
            <el-input v-model="addForm.hobby" clearable filterable allow-create :size="componentSize"
                      placeholder="请输入宠物爱好"></el-input>
          </el-form-item>

          <el-form-item label="是否上岗" prop="isWork">
            <el-select v-model="addForm.isWork" :size="componentSize" clearable placeholder="请选择是否上岗">
              <el-option
                  v-for="(item,index) in selectisWork"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="多联疫苗" prop="vaccine1">
            <el-select v-model="addForm.vaccine1" :size="componentSize" clearable placeholder="请选择已接种多联疫苗针数">
              <el-option
                  v-for="(item,index) in selectVaccine1"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="狂犬疫苗" prop="vaccine2">
            <el-select v-model="addForm.vaccine2" :size="componentSize" clearable placeholder="请选择是否接种狂犬疫苗">
              <el-option
                  v-for="(item,index) in selectVaccine2"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="健康情况" prop="health">
            <el-select v-model="addForm.health" :size="componentSize" clearable placeholder="请选择健康状况">
              <el-option
                  v-for="(item,index) in selectHealth"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
<!--          <el-form-item label="铲屎官" prop="master">-->
<!--            <el-select v-model="addForm.master" :size="componentSize" clearable placeholder="请选择铲屎官">-->
<!--              <el-option-->
<!--                  v-for="(item,index) in selectMaster"-->
<!--                  :key="index"-->
<!--                  :label="item.username"-->
<!--                  :value="item.id"-->
<!--              >-->
<!--              </el-option>-->
<!--            </el-select>-->
<!--          </el-form-item>-->

<!--          <el-form-item label="状态" prop="status">-->
<!--            <el-select v-model="addForm.status" :size="componentSize" clearable placeholder="请选择宠物状态">-->
<!--              <el-option-->
<!--                  v-for="(item,index) in selectStatus"-->
<!--                  :key="index"-->
<!--                  :label="item.name"-->
<!--                  :value="item.value"-->
<!--              >-->
<!--              </el-option>-->
<!--            </el-select>-->
<!--          </el-form-item>-->

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
  name: "Ccat",
  data() {
    return {
      serverIp: serverIp,
      form: {
        name: '',
        sex: '',
        isWork: '',
      },
      addForm: {
        name: '',
        sex: '',
        isWork: '',
        picture: '',
      },
      selectMaster:[],
      addFormBug: {
        name: '',
        sex: '',
        isWork: '',
        picture: '',
      },
      rightVisible: false,
      //展示是否在加载中
      loading: false,
      tableData: null,
      pageNum: 1,
      pageSize: 5,
      page: null,
      //记录总数
      total: 0,
      title: "",
      submitName: '保存',
      ccatList: [],
      selectSex: [{
        name: '小公猫',
        value: '1',
      },
        {
          name: '小母猫',
          value: '0',
        }
      ],
      selectisWork: [{
        name: '上岗',
        value: '1',
      },
        {
          name: '摆烂',
          value: '0',
        }
      ],
      selectStatus: [{
        name: '居家',
        value: '2',
      },
        {
          name: '流浪',
          value: '1',
        },
        {
          name: '寄养',
          value: '0',
        }
      ],
      judgeRole:null,
      selectVaccine1: [{
        name: '0',
        value: '0',
      },
        {
          name: '1',
          value: '1',
        },
        {
          name: '2',
          value: '2',
        },
        {
          name: '3',
          value: '3',
        }
      ],
      selectVaccine2: [{
        name: '已接种',
        value: '0',
      },
        {
          name: '未接种',
          value: '1',
        }
      ],
      selectHealth: [{
        name: '健康',
        value: '0',
      },
        {
          name: '生病',
          value: '1',
        }
      ],
    }
  },
  computed: {
    ...mapState(["componentSize", "componentBtnSize"]),
  },
  created() {
    this.queryAllCcatList();
    this.getSelectMaster();
    this.judgeRole  =  localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).role: ""
  },
  methods: {
    getSelectMaster(){
      this.$axios.selectMaster().then((res) => {
        this.selectMaster = res.beans
      })
    },
    onSubmit() {
      this.queryAllCcatList()
    },
    reOnSubmit() {
      this.pageNum = 1;
      this.pageSize = 5;
      this.form = {};
      this.page = null;
      this.queryAllCcatList()
    },
    modifyCcat(val) {
      this.addForm = JSON.parse(JSON.stringify(val.row))
      console.log("123123", this.addForm)
      this.title = "修改宠物";
      this.rightVisible = true;
    },
    changeToWork(val){
      if (val.row.isWork == 1){
        this.$message({
          title: "警告",
          message: "该喵已经在打工啦！",
          type: "success",
          duration: 2000,
        });
        return
      }
      this.$axios.changeCcatToWork(val.row).then((res) => {
        if (res.returnCode == "200") {
          this.$notify({
            title: "派遣成功",
            message: "该喵马上上岗！",
            type: "success",
            duration: 2000,
          });
          this.onSubmit();
        } else {
          this.$notify.error({
            title: "错误",
            message: "派遣异常",
            duration: 2000,
          });
        }
      })
    },
    deleteCcat(val) {
      if (val.row.id != null && val.row.id != "") {
        this.$axios.deleteCcat(val.row).then((res) => {
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
          message: "删除异常！",
          type: "warning",
          duration: 1000,
        });
      }
    },
    //选择一页显示多少行数据
    handleSizeChange(val) {
      this.pageSize = val;
      this.queryAllCcatList()
    },
    //显示第几页
    pageChange(val) {
      this.pageNum = val;
      this.queryAllCcatList()
    },
    queryAllCcatList() {
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.form.name,
        sex: this.form.sex,
        isWork: this.form.isWork,
        master: this.form.master,
        status: this.form.status,
        vaccine1: this.form.vaccine1,
        vaccine2: this.form.vaccine2,
        health: this.form.health,
      };
      this.loading = true;
      this.tableData = [];
      this.$axios.queryAllBcatList(params).then(res => {
        this.tableData = res.beans
        this.total = res.totalCount;
        this.loading = false;
      });
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
      this.ccatList = rows;
    },
    addCcat() {
      this.title = "新增宠物";
      this.addForm = JSON.parse(JSON.stringify(this.addFormBug))
      // this.addForm = {};
      this.rightVisible = true;
    },
    deleteAll() {
      if (this.ccatList == null || this.ccatList.length == 0) {
        this.$notify({
          title: "警告",
          message: "请至少选择一条数据进行删除",
          type: "warning",
          duration: 1000,
        });
      } else {
        this.$confirm('确定删除这些数据吗？', '提示', {type: 'warning'}).then(() => {
          this.$axios.deleteAllCcat(this.ccatList).then(res => {
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
    insertSubmit() {
      if (this.title == "新增宠物") {
        this.$axios.addBcat(this.addForm).then(res => {
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
        this.$axios.addBcat(this.addForm).then(res => {
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
    //关闭编辑弹窗回调
    addRoleEditDialogClose() {
      this.rightVisible = false;
      this.onSubmit();
    },
    //关闭文档编辑弹窗不提交
    addRoleEditDialogNoSubmit() {
      this.rightVisible = false;
    },
    handleAvatarSuccess(res) {
      console.log("上传头像res：" + res)
      // debugger
      this.addForm.picture = res
      this.refreshBug()
    },
    refreshBug() {
      console.log("奇奇怪怪")
    },
    pageToDcat(){
      this.$router.push("/Dcat")
    }
  }
}
</script>

<style scoped>

</style>