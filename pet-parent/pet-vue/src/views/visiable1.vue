<template>
  <div>
    <el-col :span="24">
      <el-form
          :inline="true"
          :model="form"
          class="select-no-form"
          label-width="100px"
      >
        <el-row class="row1">
          <el-form-item label="预约人" prop="userId">
            <el-select v-model="form.userId" :size="componentSize" filterable clearable placeholder="请选择预约人">
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
            <el-select v-model="form.meal" :size="componentSize" clearable placeholder="请选择套餐">
              <el-option
                  v-for="(item,index) in selectMeal"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row class="row1">

          <el-form-item label="预约时间" prop="time">
            <el-date-picker
                v-model="form.time"
                type="datetime"
                placeholder="选择日期时间"
                default-time="12:00:00">
            </el-date-picker>
          </el-form-item>

        </el-row>
      </el-form>
      <el-button style="margin: 12px 20px 0 0 ;float: right;" @click="next">{{nextName}}</el-button>
    </el-col>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      form: {
        userId :""
      },
      nextName:"下一步",
      selectMaster: [],
      time: '',
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
  created() {
    this.getSelectMaster()
  },
  computed: {
    ...mapState(["componentSize", "componentBtnSize"]),
  },
  methods: {
    getSelectMaster() {
      this.$axios.selectMaster().then((res) => {
        this.selectMaster = res.beans
      })
    },
    next(){
      console.log("当前点击下一步信息--------");
      // console.log(this.$parent);
      this.$axios.addMealList(this.form).then((res) => {
        if (res.returnCode != "200") {
          this.$notify.error({
            title: "错误",
            message: "数据有误！",
            duration: 1000,
          });
          return
        }
      })

      this.$parent.$parent.$parent.$parent.$parent.nextPage(1);
    },
    previous(){
      // console.log(this.$parent);
      this.$parent.$parent.$parent.$parent.$parent.nextPage(0);
    },
  }
}
</script>

<style>
.row1{
  margin: 10px 0 10px 0;
}
</style>
