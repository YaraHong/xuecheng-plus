<template>
  <div class="course-list portal-content">
    <div class="workspace">
      <div class="banner">
        <span class="primary-title">作业批改</span>
      </div>

      <!-- 搜索栏 -->
      <div class="searcher">
        <el-input
            v-model="listQueryData.courseName"
            class="el-input"
            placeholder="请输入课程名称"
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
        <el-table-column align="center" label="课程名称" prop="courseName" width="400"></el-table-column>

        <el-table-column align="center" label="答题人数" prop="totalUsers"></el-table-column>

        <el-table-column align="center" label="待批阅数" prop="tobeReviewed"></el-table-column>

        <el-table-column align="center" label="最后提交/最后批阅">
          <template slot-scope="scope">
            {{ scope.row.commitedTime | dateTimeFormat }}
            <br/>
            {{ scope.row.reviewedTime | dateTimeFormat }}
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="text"
                @click="goToWorkRecordReadOverAllView(scope.row.courseWorkId)"
            >批阅
            </el-button>
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
            @pagination="getWorkRecordPageList"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator'
import Pagination from '@/components/pagination/index.vue'
import {IWorkRecordPageVO} from '@/entity/work-record-page-list'
import {getWorkRecordPageList} from '@/api/work-record'

@Component({
  components: {
    Pagination
  }
})
export default class WorkRecordList extends Vue {
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
  // 作业提交列表
  private listResult: IWorkRecordPageVO = {}

  /**
   * 作业提交列表
   */
  private async getWorkRecordPageList() {
    this.listLoading = true
    this.listResult = await getWorkRecordPageList(
        this.listQuery,
        this.listQueryData
    )
    this.listLoading = false
  }

  /**
   * 跳转到批阅详情页面
   */
  private goToWorkRecordReadOverAllView(courseWorkId: number) {
    this.$router.push({
      path: `/organization/work-record-overall?courseWorkId=${courseWorkId}`
    })
  }

  // 监控 watch
  // 搜索栏
  @Watch('listQueryData', {deep: true, immediate: true})
  private watchListQueryData(newVal: string) {
    if (newVal == '') {
      return
    }
    this.getWorkRecordPageList()
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

