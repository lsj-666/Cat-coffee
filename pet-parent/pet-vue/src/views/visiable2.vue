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
          <el-form-item label="预约人" prop="username">
            <el-select disabled="true" v-model="form.userId" :size="componentSize" filterable clearable placeholder="请选择预约人">
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
            <el-select disabled="true" v-model="form.meal" :size="componentSize" clearable placeholder="请选择套餐">
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
                disabled="true"
                v-model="form.time"
                type="datetime"
                placeholder="选择日期时间"
                default-time="12:00:00">
            </el-date-picker>
          </el-form-item>

        </el-row>
      </el-form>

      <el-button style="margin: 12px 20px 0 0 ;float: right;" @click="next">{{nextName}}</el-button>
      <el-button style="margin: 12px 20px 0 0 ;float: right;" @click="previous">{{previousName}}</el-button>
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
      previousName:"上一步",
    }
  },
  created() {
    this.getSelectMaster()
    this.selectMealInfo()
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
      this.$parent.$parent.$parent.$parent.$parent.nextPage(1);
    },
    previous(){
      // console.log(this.$parent);
      this.$parent.$parent.$parent.$parent.$parent.nextPage(0);
    },
    selectMealInfo(){
      this.$axios.selectMealInfo().then((res) => {
        this.form = res.beans[0]
      })
    }
  }
}
</script>

<style>
.row1{
  margin: 10px 0 10px 0;
}
</style>
