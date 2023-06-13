<template>

  <div style="color: #666;font-size: 18px;">
    <el-card>
      <div
          slot="header"
          class="file-search-condition-title"
      >
        <span class="content-Breadcrumb">门店详情</span>
        <el-row>
          <el-col :span="6" style="padding: 20px">
            <el-card>
              <div style="color: #409EFF">喵喵总数</div>
              <div style="padding: 10px 0;text-align: center;font-weight: bold">
                <el-tag>{{ allCats }}</el-tag>
              </div>
            </el-card>
          </el-col>

          <el-col :span="6" style="padding: 20px">
            <el-card>
              <div style="color: #42b983">在店总喵数</div>
              <div style="padding: 10px 0;text-align: center;font-weight: bold">
                <el-tag type="success">{{ atStoreCats }}</el-tag>
              </div>
            </el-card>
          </el-col>

          <el-col :span="6" style="padding: 20px">
            <el-card>
              <div style="color: #ff9900">今日上岗数</div>
              <div style="padding: 10px 0;text-align: center;font-weight: bold">
                <el-tag type="warning">{{ workCats }}</el-tag>
              </div>
            </el-card>
          </el-col>

          <el-col :span="6" style="padding: 20px">
            <el-card>
              <div style="color: #e60000">顾客总数</div>
              <div style="padding: 10px 0;text-align: center;font-weight: bold">
                <el-tag type="danger">{{ customers }}</el-tag>
              </div>
            </el-card>
          </el-col>
        </el-row>


        <el-row style="padding: 20px">
          <el-col :span="12">
            <div id="main" style="width: 500px;height: 400px"></div>
          </el-col>

          <el-col :span="12">
            <div id="pie" style="width: 500px;height: 400px"></div>
          </el-col>
        </el-row>

      </div>
    </el-card>

  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data() {
    return {
      allCats: "",
      atStoreCats: "",
      workCats: "",
      customers: "",

      // user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  mounted() {
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option = {
      title: {
        text: '近期访客数',
        subtext: 'customer',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: []
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };

    this.$axios.getExample().then(res => {
      option.xAxis.data = res.object.x
      option.series[0].data = res.object.y
      option.series[1].data = res.object.y
      myChart.setOption(option)
    });

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);
    var peiOption = {
      title: {
        text: '猫咪状态总览',
        subtext: '',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '状态',
          type: 'pie',
          radius: '50%',
          data: [
            {value: 1048, name: 'Search Engine'},
            {value: 735, name: 'Direct'},
            {value: 580, name: 'Email'},
            {value: 484, name: 'Union Ads'},
            {value: 300, name: 'Video Ads'},
            {value: 300, name: '123 Ads'}
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    this.$axios.getPieExample().then(res => {
      peiOption.series[0].data = res.object
      pieChart.setOption(peiOption)
    });

  },
  created() {
    this.getAllCats();
    this.getAtStoreCats();
    this.getWorkCats();
    this.getCustomers();
  },
  methods: {
    getAllCats() {
      this.$axios.getAllCats().then(res => {
        this.allCats = res.object
      })
    },
    getAtStoreCats() {
      this.$axios.getAtStoreCats().then(res => {
        this.atStoreCats = res.object
      })
    },
    getWorkCats() {
      this.$axios.getWorkCats().then(res => {
        this.workCats = res.object
      })
    },
    getCustomers() {
      this.$axios.getCustomers().then(res => {
        this.customers = res.object
      })
    },

  }
}
</script>
