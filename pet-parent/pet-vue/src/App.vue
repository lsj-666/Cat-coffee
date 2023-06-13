<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      size: 1.0,
    };
  },
  mounted() {
    if (window.screen.height <= 900) {
      this.$store.commit("componentBtnSizeChange", "mini");
    }
  },
};
</script>

<style>
</style>
