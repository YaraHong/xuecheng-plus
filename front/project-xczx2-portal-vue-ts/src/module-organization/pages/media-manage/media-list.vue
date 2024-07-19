<template>
  <div class="course-list portal-content">
    <div class="workspace">
      <div class="banner">
        <span class="primary-title">媒资管理</span>
        <el-button
            class="btn-add el-button"
            size="medium"
            type="primary"
            @click="handleOpenAddMediaDialog"
        >上传视频
        </el-button>
      </div>

      <!-- 搜索栏 -->
      <div class="searcher">
        <el-input
            v-model="listQueryData.filename"
            class="el-input"
            placeholder="请输入关键词"
            suffix-icon="el-icon-search"
        />
        <el-select v-model="listQueryData.fileType" placeholder="请选择文件类型">
          <el-option
              v-for="item in mediaTypes"
              :key="item.code"
              :label="item.desc"
              :value="item.code"
          ></el-option>
        </el-select>
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
        <el-table-column align="center" label="文件名称" width="400">
          <template slot-scope="scope">
            {{ scope.row.filename }}
            <!--<br />
            视频时长：{{ scope.row.timelength }}-->
          </template>
        </el-table-column>
        <el-table-column align="center" label="类型">
          <template slot-scope="scope">
            {{ getMediaTypes(scope.row.fileType) }}

          </template>
        </el-table-column>
        <el-table-column align="center" label="标签" prop="tags"></el-table-column>

        <el-table-column align="center" label="上传时间">
          <template slot-scope="scope">
            {{ scope.row.username }}
            <br/>
            {{ scope.row.createDate | dateTimeFormat }}
          </template>
        </el-table-column>

        <el-table-column align="center" label="审核状态">
          <template slot-scope="scope">
            {{ getMediaAuditStatus(scope.row.auditStatus) }}
            <br/>
            <el-button
                v-if="scope.row.auditStatus === '002001'"
                type="text"
                @click="handleViewAuditMind(scope.row.auditMind)"
            >点击查看原因
            </el-button>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button :disabled="scope.row.url == undefined" size="mini" type="text"
                       @click="handleDownloadMedia(scope.row.id)">预览
            </el-button>

            <el-button
                size="mini"
                type="text"
                @click="handleOpenDeleteMediaConfirm(scope.row.id)"
            >移除
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
            @pagination="getMediaPageList"
        />
      </div>
    </div>

    <!-- 上传资料对话框 -->
    <media-add-dialog :dialogVisible.sync="dialogVisible" @closeDialog="getMediaPageList"></media-add-dialog>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator'
import Pagination from '@/components/pagination/index.vue'
import MediaAddDialog from './components/media-add-dialog.vue'
import {IKVData} from '@/api/types'
import {MEDIA_AUDIT_STATUS, RESOURCE_STATUS} from '@/api/constants'
import {IMediaPageList} from '@/entity/media-page-list'
import {deleteMedia, getMediaPageList, previewMedia} from '@/api/media'

@Component({
  components: {
    Pagination,
    MediaAddDialog
  }
})
export default class MediaList extends Vue {
  // 资料类型列表
  private mediaTypes: IKVData[] = RESOURCE_STATUS
  // 审核状态列表
  private mediaAuditStatus: IKVData[] = MEDIA_AUDIT_STATUS


  // 是否载入中
  private listLoading: boolean = false
  // 请求参数Query
  private listQuery = {
    pageNo: 1,
    pageSize: 10
  }
  // 请求参数body
  private listQueryData = {
    filename: '',
    fileType: ''
  }
  // 媒资列表
  private listResult: IMediaPageList = {}
  // 上传资料对话框
  private dialogVisible: boolean = false

  // 计算属性
  getMediaAuditStatus(auditStatus: string) {
    let item = this.mediaAuditStatus.find((value: IKVData) => {
      return auditStatus == value.code
    })
    return !item ? '' : item.desc
  }

  getMediaTypes(fileType: string) {
    let item = this.mediaTypes.find((value: IKVData) => {
      return fileType == value.code
    })
    return !item ? '' : item.desc
  }


  /**
   * 媒资列表
   */
  private async getMediaPageList() {
    this.listLoading = true
    this.listResult = await getMediaPageList(this.listQuery, this.listQueryData)
    this.listLoading = false
  }

  /**
   * 上传资料
   */
  private handleOpenAddMediaDialog() {
    this.dialogVisible = true
  }

  /**
   * 查看原因
   */
  private handleViewAuditMind(auditMind: string) {
    this.$message({
      showClose: true,
      message: auditMind
    })
  }

  /**
   * 下载媒资
   */
  private async handleDownloadMedia(id: number) {
    let res = await previewMedia(id);
    if (res && res.code == 0) {
      window.open(`${process.env.VUE_APP_SERVER_PICSERVER_URL}` + res.result);
    } else {
      this.$message({
        type: 'error',
        message: res.errMessage
      })
    }

    return;
  }

  // private async handleDownloadMedia(id: number) {
  //   let playUrl= await previewMedia(id);
  //   if(!playUrl){
  //     this.$message({
  //     type: 'error',
  //     message: "视频没有转码处理"
  //   })
  //     return;
  //   }
  //   window.open("/api/media/test?url="+`${process.env.VUE_APP_SERVER_PICSERVER_URL}` +"/"+playUrl);
  // }

  /**
   * 移除媒资
   */
  private handleOpenDeleteMediaConfirm(id: number) {
    this.$confirm('此操作将永久移除该文件, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
        .then(async () => {
          let flag: boolean = await deleteMedia(id)
          this.getMediaPageList()
          this.$message({
            type: 'success',
            message: '移除成功!'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消移除'
          })
        })
  }

  // 监控 watch
  // 搜索栏
  @Watch('listQueryData', {deep: true, immediate: true})
  private watchListQueryData(newVal: string) {
    if (newVal == '') {
      return
    }
    this.getMediaPageList()
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
