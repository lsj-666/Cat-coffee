<template>
  <div style="overflow: hidden; height: 100vh">
    <img src="../assets/img/404.jpg" alt="" style="width: 100%; height: 100%" @click="goHome">
  </div>
</template>

<script>
export default {
  name: "NotFound",
  methods:{
    goHome(){
      console.log("404444444")
      // this.$router.push("/")
    }
  }
}
</script>

<style>
.bgImg {
  background: url("../assets/img/404.jpg") no-repeat;
  background-size: 100% 100vh;
}
</style>
