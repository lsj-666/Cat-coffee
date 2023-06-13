<template>
  <div>
    <el-row>
      <img src="../assets/img/wx.jpg" style="width: 300px; margin: 0 50px 0 50px">
      <img src="../assets/img/zfb.jpg" style="width: 300px; ">
    </el-row>

    <el-button style="margin: 12px 20px 0 0 ;float: right;" @click="next">{{nextName}}</el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nextName:"完成",
    }
  },
  methods:{
    next(){
      console.log("当前点击下一步信息--------");
      // console.log(this.$parent);
      this.$parent.$parent.$parent.$parent.$parent.nextPage(2);
    },
  }
}
</script>

<style>
</style>
