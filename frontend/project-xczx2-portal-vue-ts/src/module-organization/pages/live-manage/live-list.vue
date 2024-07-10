<template>
  <div class="course-list portal-content">
    <div class="workspace">
      <div class="banner">
        <span class="primary-title">直播管理</span>
      </div>

      <!-- 搜索栏 -->
      <div class="searcher">
        <el-input
            v-model="listQueryData.courseName"
            class="el-input"
            placeholder="直播列表"
            suffix-icon="el-icon-search"
        />
      </div>

      <!-- 数据列表 -->
      <el-table
          v-loading="listLoading"
          :data="listResult.items"
          :header-cell-style="{ textAlign: 'center' }"
          border
          class="dataList"
          style="width: 100%"
      >
        <el-table-column align="center" label="课程信息" prop="courseName" width="400"></el-table-column>

        <el-table-column align="center" label="课程大纲" prop="teachplanName"></el-table-column>

        <el-table-column align="center" label="直播时间">
          <template slot-scope="scope">{{ scope.row.liveStart | dateTimeFormat }}</template>
        </el-table-column>

        <el-table-column align="center" label="类型">
          <template slot-scope="scope">{{ scope.row.isPreview === '1' ? '免费' : '收费' }}</template>
        </el-table-column>

        <el-table-column align="center" label="推流地址">
          <template slot-scope="scope">
            <span v-if="scope.row.pushUrl">{{ scope.row.pushUrl }}</span>
            <el-button v-else size="mini" type="text" @click="handleGetPushUrl(scope.row)">获取推流地址</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 翻页控制 -->
      <div class="dataList-pagination">
        <Pagination
            v-show="listResult.counts > 0"
            :limit.sync="listQuery.pageSize"
            :page.sync="listQuery.pageNo"
            :total="listResult.counts"
            @pagination="getLivePageList"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator'
import Pagination from '@/components/pagination/index.vue'
import {ILiveCourseDTO, ILivePageList, IPushUrlDTO} from '@/entity/live-page-list'
import {getLivePageList, getPushUrl} from '@/api/live'

@Component({
  components: {
    Pagination
  }
})
export default class LiveList extends Vue {
  // 是否载入中
  private listLoading: boolean = false
  // 请求参数Query
  private listQuery = {
    pageNo: 1,
    pageSize: 10
  }
  // 请求参数body
  private listQueryData = {
    courseName: ''
  }
  // 直播列表
  private listResult: ILivePageList = {}

  /**
   * 直播列表
   */
  private async getLivePageList() {
    this.listLoading = true
    this.listResult = await getLivePageList(this.listQuery, this.listQueryData)
    this.listLoading = false
  }

  /**
   * 获取推流地址
   */
  private async handleGetPushUrl(row: ILiveCourseDTO) {
    let result: IPushUrlDTO = await getPushUrl(row)
    if (result.state !== 0) {
      this.$message.error(`获取推流地址失败：${result.message}`)
      return
    }
    row.pushUrl = result.pushUrl
  }

  // 监控 watch
  // 搜索栏
  @Watch('listQueryData', {deep: true, immediate: true})
  private watchListQueryData(newVal: string) {
    if (newVal == '') {
      return
    }
    this.getLivePageList()
  }

  // 翻页 pageSize
  @Watch('listQuery.pageSize', {immediate: true})
  private watchListQueryPageSize(newVal: number) {
    this.listQuery.pageNo = 1
  }
}
</script>

<style lang="scss" scoped>
.course-list {
  .nav-bar {
    margin-top: 16px;
  }

  .workspace .banner .btn-add {
    float: right;
  }

  .searcher {
    margin-top: 16px;

    div {
      width: 218px;
      margin-right: 10px;
    }
  }

  .dataList {
    margin-top: 16px;
  }

  .dataList-pagination {
    text-align: center;
    width: 100%;
  }
}
</style>
