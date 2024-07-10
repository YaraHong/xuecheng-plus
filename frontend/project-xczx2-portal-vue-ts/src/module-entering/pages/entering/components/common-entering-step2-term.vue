<template>
  <el-row>
    <el-col :span="12">
      <el-radio-group v-model="type" @change="handleTypeChange">
        <el-radio :label="0">长期有效</el-radio>
        <el-radio :label="1">固定期限</el-radio>
      </el-radio-group>
    </el-col>
    <el-col :span="12">
      <el-date-picker
          v-model="syncedTerm"
          :disabled="type === 0 ? true : false"
          placeholder="选择日期"
          style="width: 100%;"
          type="date"
          value-format="yyyy-MM-dd"
      ></el-date-picker>
    </el-col>
  </el-row>
</template>

<script lang="ts">
import {Component, PropSync, Vue} from 'vue-property-decorator'

@Component
export default class CommonEnteringStep2Term extends Vue {
  @PropSync('term', {type: String, required: false, default: ''})
  syncedTerm!: string

  private type: number = 1

  /**
   * 长期有效/固定期限
   */
  private handleTypeChange(val: number) {
    this.syncedTerm = val === 0 ? '2099-12-31' : ''
  }
}
</script>