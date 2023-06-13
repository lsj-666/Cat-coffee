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
        <span class="content-Breadcrumb">文件检索</span>
      </div>

    </el-card>


  </div>

</template>

<script>
export default {
  name: "fileSearch",
  created() {
    // 从后台获取最新的User数据
   this.$axios.findAll();
  },
  data(){
    return{

    }
  }
}
</script>

<style lang="scss" scoped>


</style>