<template>
  <div>
    <el-card
        class="main-card"
        shadow="hover"
        style="min-height: 600px"
    >

      <div>
        <template>
          <el-carousel :interval="4000" type="card" height="350px" style="margin: 20px 100px 20px 100px">
            <el-carousel-item v-for="item in items" :key="item.id">
              <img style="background-size: 100% 100%;;width: 100%;height: 100%" :src="item.idView">
<!--              background-size: 100% 100%;-->
            </el-carousel-item>
          </el-carousel>
        </template>

        <div>
          <el-col
              :span="1"
              class="tab-left-col"
          >
            <el-card
                class="box-card"
                shadow="hover"
            >
              <div
                  slot="header"
                  class="file-search-condition-title"
              >
                <span class="content-Breadcrumb" style="font-size: 16px">今日优惠套餐</span>
              </div>

              <el-row >
                <el-tag class="label1" type="primary" round>套餐一</el-tag>
                <span class="span1">48元单人不限时撸猫体验券：</span>
                <span>单人不限时撸猫体验券</span>
              </el-row>
              <el-row >
                <el-tag class="label1" type="primary" round>套餐二</el-tag>
                <span class="span1">58元单人不限时撸猫体验券：</span>
                <span>单人不限时撸猫体验券</span>
              </el-row>
              <el-row>
                <el-tag class="label1" type="primary" round>套餐三</el-tag>
                <span class="span1">68元单人不限时撸猫体验券：</span>
                <span>单人不限时撸猫体验券</span>
              </el-row>
              <el-row>
                <el-tag class="label1" type="primary" round>套餐四</el-tag>
                <span class="span1">78元单人不限时撸猫体验券：</span>
                <span>单人不限时撸猫体验券</span>
              </el-row>

              <el-row style="margin: 40px ; ">
                <el-button style="float: right;" type="success" round @click="buyTicket">预约购票</el-button>
              </el-row>
            </el-card>
          </el-col>


          <el-col
              :span="1"
              class="tab-right-col"
          >
            <el-card
                class="box-card"
                shadow="hover"
            >
              <el-button type="primary" round>公告栏</el-button>
              <el-row >
                <el-tag class="label1" type="primary" round>一、</el-tag>
                <span class="span2">单人不限时撸猫体验券：</span><br>
                <span>单人不限时撸猫体验券</span>
              </el-row>
              <el-row >
                <el-tag class="label1" type="primary" round>二、</el-tag>
                <span class="span2">单人不限时撸猫体验券：</span><br>
                <span>单人不限时撸猫体验券</span>
              </el-row>
              <el-row>
                <el-tag class="label1" type="primary" round>三、</el-tag>
                <span class="span2">单人不限时撸猫体验券：</span><br>
                <span>单人不限时撸猫体验券</span>
              </el-row>
            </el-card>
          </el-col>
        </div>

      </div>
      <el-dialog
          :title="title"
          :close-on-click-modal='false'
          :visible.sync="visiable"
          :modal-append-to-body='false'
          append-to-body
          width="800px">
        <el-row >
          <el-col>
            <el-steps
                space="100%"
                :active="active"
                finish-status="success"
                align-center
                style="margin: 10px 0 20px 0">
              <el-step title="预约登记"></el-step>
              <el-step title="确认信息"></el-step>
              <el-step title="支付"></el-step>
            </el-steps>
            <visiable1
                @visiable1="getVisiable1"
                v-if="visiable1"
            />
            <visiable2
                @visiable2="getVisiable2"
                v-if="visiable2"
            />
            <visiable3
                @visiable3="getVisiable3"
                v-if="visiable3"
            />



          </el-col>


        </el-row>

      </el-dialog>
    </el-card>

  </div>
</template>

<script>
import visiable1 from "./visiable1.vue";
import visiable2 from "./visiable2.vue";
import visiable3 from "./visiable3.vue";
export default {
  name: "Shop",
  components:{
    visiable1,
    visiable2,
    visiable3,
  },
  data() {
    return {
      items: [
        {id: 1, idView: require('../assets/img/1.jpg')},
        {id: 2, idView: require('../assets/img/2.jpg')},
        {id: 3, idView: require('../assets/img/3.jpg')},
        {id: 4, idView: require('../assets/img/4.jpg')},
        {id: 5, idView: require('../assets/img/5.jpg')},
        {id: 6, idView: require('../assets/img/6.jpg')},
      ],
      item: '',
      visiable:false,
      title:'预约购票',
      active: 0,
      visiable1: true,
      visiable2: false,
      visiable3: false,
      nextName: "下一步",
      form:{
        getVisiable1:{}
      }
    }
  },
  methods:{
    buyTicket(){
      this.visiable = true;
    },
    // next() {
    //   if (this.active++ > 2) this.active = 0;
    //   this.ifPage();
    // },
    getVisiable1(val){
      this.form.getVisiable1 = val;
    },
    getVisiable2(val){

    },
    getVisiable3(val){

    },
    nextPage(val) {
      console.log("当前点击下一步信息------nextPage-----");
      if (val == "1") {
        this.active++;
      } else if (val == "0") {
        this.active --;
      } else if (val == "2") {
        this.active = 0;
        this.visiable = false;
        this.$message.success("预约成功！！！")
      }
      this.ifPage();
    },
    ifPage() {
      console.log("当前ifPage的active"+this.active);
      if (this.active == "0") {
        this.visiable1 = true;
        this.visiable2 = false;
        this.visiable3 = false;
      }else if (this.active == "1"){
        this.visiable1 = false;
        this.visiable2 = true;
        this.visiable3 = false;
      }else if (this.active == "2"){
        this.visiable1 = false;
        this.visiable2 = false;
        this.visiable3 = true;
      }
    }
  },
}
</script>

<style scoped lang="scss" scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.tab-left-col {
  width: 70%;

  .box-card {
    overflow: auto;
    min-height: 400px;
    max-height: 700px;
  }
  margin-top:10px

}

.tab-right-col {
  width: calc(100% - 70%);
  .box-card {
    overflow: auto;
    min-height: 450px;
    max-height: 700px;
  }
  margin-top:10px
}
.label1{
  margin: 20px 20px 10px 20px ;
  font-size: 20px
}
.span1{
  margin: 0 0 0 30px;
  font-size: 20px;
  font-weight: bold;
}
.span2{
  font-size: 20px;
  font-weight: bold;
}
</style>