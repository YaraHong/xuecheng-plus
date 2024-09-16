<template>
  <el-dialog :visible.sync="syncDialogVisible" title="选择课程形式" width="500px">
    <div class="types">
      <div v-for="(item, index) in courseTypes" :key="index" class="item">
        <CourseAddTypeItem
            :addtype="item.code"
            :selectedTypeCode.sync="selectedTypeCode"
            :title="item.title"
            class="selected-item"
        ></CourseAddTypeItem>
      </div>
    </div>
    <div slot="footer">
      <el-button type="primary" @click="handleNext">下一步</el-button>
    </div>
  </el-dialog>
</template>

<style lang="scss" scoped>
.types {
  height: 220px;

  .item {
    display: inline-block;
    width: 50%;
    height: 100%;
    // background-color: bisque;
    padding-top: 20px;

    .selected-item {
      margin: 0px auto;
    }
  }
}
</style>

<script lang="ts">
import {Component, PropSync, Vue} from 'vue-property-decorator'
import CourseAddTypeItem from './course-addtype-item.vue'

@Component({
  name: 'CourseAddTypeDialog',
  components: {CourseAddTypeItem}
})
export default class extends Vue {
  @PropSync('dialogVisible', {type: Boolean, default: false})
  syncDialogVisible!: boolean

  // 选中的课程类型
  private selectedTypeCode: string = '200003'

  // 课程类型
  private courseTypes = [
    {
      code: '200003',
      title: '直播课程'
    },
    {
      code: '200002',
      title: '录播课程'
    }
  ]

  created() {
  }

  handleNext() {
    this.$router.push({
      path: `/organization/course-add?teachmode=${this.selectedTypeCode}`
    })
  }
}
</script>
