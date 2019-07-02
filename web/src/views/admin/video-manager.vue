<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.video_name" placeholder="书名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.ptag" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in ptaglist" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        导出
      </el-button>
    </div>

    <el-table
    :key="tableKey"
    v-loading="listLoading"
    :data="list"
    border
    fit
    highlight-current-row
    style="width: 100%;"
    >
      <el-table-column label="序号" prop="id" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="图片" prop="image" width="110px" align="center">
        <template slot-scope="scope">
          <a :href="scope.row.out_link" target="_blank">
            <img :src="scope.row.pic_link"  min-width="70" height="70" />
          </a>
        </template>
      </el-table-column>
      <el-table-column label="视频名称" width="350px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.video_name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="时长" width="150px">
        <template slot-scope="{row}">
          <span class="link-type">{{ durationOption[row.duration]}}</span>
        </template>
      </el-table-column>
      <el-table-column label="语言" width="110px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.producer }}</span>
        </template>
      </el-table-column>
      <el-table-column label="视频地址" min-width="480px">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button  size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="80px" style="width: 400px; margin-left:50px;">
        <el-form-item label="标签">
          <div style="margin-bottom:10px; ">
            <el-radio-group v-model="temp.ptag" @change="ptagChange">
              <el-radio-button :label="item.id" v-for="item in ptaglist">{{item.name}}</el-radio-button>
            </el-radio-group>
          </div>
          <div>
            <el-checkbox-group v-model="checkedCtags">
              <el-checkbox-button v-for="item in ctaglist" :label="item.id" :key="item.id">{{item.name}}</el-checkbox-button>
            </el-checkbox-group>
          </div>
        </el-form-item>
        <el-form-item label="图片" prop="pic_link">
          <el-upload
            ref="imgUpload"
            :action="uploadApi"
            :show-file-list="false"
            :data="{category_id:video_id}"
            :before-upload="handleBeforeUpload"
            :on-success="handlePicUploadSuccess">
            <img v-if="temp.pic_link" :src="temp.pic_link" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="视频名称" prop="video_name">
          <el-input v-model="temp.video_name" />
        </el-form-item>
        <el-form-item label="时长" prop="duration">
          <el-select v-model="temp.duration" value-key="id" clearable style="width: 150px" class="filter-item">
            <el-option v-for="item in durationOptions" :key="item.id" :label="item.label" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="语言" prop="producer">
          <el-input v-model="temp.producer" />
        </el-form-item>
        <el-form-item label="外链地址" prop="out_link">
          <el-input v-model="temp.out_link" />
        </el-form-item>
        <el-form-item label="视频地址">
          <el-input v-model="temp.description" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
<script>
import { getAllVideos, createVideo, updateVideo, deleteVideo } from '@/api/video'
import {getParentTags,getAllTags,getGoodsTag} from '@/api/goods'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const video_id = 3;
const durationOption = {0: '10m以下', 1: '10~20m', 2: '20~30m', 3: '30~40m', 4: '40~60m', 6: '60m以上'};
export default {
  name: 'videoManager',
  components: { Pagination },
  directives: { waves },
  filters: {
  },
  data() {
    return {
      video_id,
      durationOption,
      uploadApi: this.GLOBAL.uploadApi,
      checkedCtags: [],
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        video_name: undefined,
        ptag: undefined
      },
      ptagQuery: {
        category_id: video_id
      },
      ptaglist: null,
      ptag2ctaglist: null,
      ctaglist: null,
      temp: {
        id: undefined,
        pic_link: '',
        video_name: '',
        duration: '',
        producer: '',
        out_link: '',
        description: '',
        ptag: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      rules: {
        video_name: [{ required: true, message: 'video_name is required', trigger: 'blur' }],
        producer: [{ required: true, message: 'producer is required', trigger: 'change' }],
        ptag: [{ required: true, message: 'ptag is required', trigger: 'change' }]
      },
      downloadLoading: false,
      durationOptions: [{
        id: 0,
        label: '10m以下'
      }, {
        id: 1,
        label: '10~20m'
      }, {
        id: 2,
        label: '20~30m'
      }, {
        id: 3,
        label: '30~40m'
      }, {
        id: 4,
        label: '40~60m'
      }, {
        id: 6,
        label: '60m以上'
      }]
    }
  },
  created() {
    this.getList()
    this.fetchTag()
  },
  methods: {
    getList() {
      this.listLoading = true
      getAllVideos(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    fetchTag(){
      getParentTags(this.ptagQuery).then(response => {
        this.ptaglist = response.data.items
      })
      getAllTags(this.ptagQuery).then(response => {
        this.ptag2ctaglist = response.data
      })
    },
    ptagChange(item) {
      this.ctaglist = this.ptag2ctaglist[this.temp.ptag]
      this.checkedCtags = []
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        video_name: '',
        duration: '',
        producer: '',
        out_link: '',
        pic_link: '',
        description: '',
        ptag: ''
      }
      this.ctaglist = []
    },
    handleBeforeUpload(file) {
      if (!(file.type === 'image/png' || file.type === 'image/jpg' || file.type === 'image/jpeg')) {
        this.$notify.warning({
          title: '警告',
          message: '请上传格式为image/png, image/jpg的图片'
        })
      }
      const size = file.size / 1024 / 1024
      if (size > 1) {
        this.$notify.warning({
          title: '警告',
          message: '图片大小必须小于1M'
        })
      }
    },
    handlePicUploadSuccess: function(res, file) {
      if (res.code === 20000) {
        this.temp.pic_link = res.data
      } else {
        this.temp.pic_link = ''
        this.$notify.error({
          title: '失败',
          message: '图片上传失败'
        })
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (!this.temp.pic_link ) {
            this.$notify.error({
              title: '图片缺失',
              message: '请上传图片'
            })
          } else if(!this.temp.ptag){
            this.$notify.error({
              title: '标签缺失',
              message: '请选择标签'
            })
          }else {
            const tempData = Object.assign({}, this.temp)
            if (this.checkedCtags.length > 0){
              tempData.ctags = this.checkedCtags.join(",")
            } else {
              tempData.ctags = '';
            }
            createVideo(tempData).then(response => {
              var code = response.code
              if (code == 20000) {
                this.temp.id = response.data;
                this.list.unshift(this.temp)
                this.dialogFormVisible = false
                this.$notify({
                  title: '成功',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                })
              }else{
                this.$alert({
                  title: '失败',
                  message: '创建创建',
                  duration: 2000
                })
              }
            })
          }
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      getGoodsTag({category_id:video_id,goods_id:row.id}).then(response => {
        this.temp.ptag = response.data.ptag
        this.checkedCtags = response.data.ctags
        this.ctaglist = this.ptag2ctaglist[this.temp.ptag]
      })
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid && this.temp.ptag) {
          const tempData = Object.assign({}, this.temp)
          if (this.checkedCtags && this.checkedCtags.length > 0){
            tempData.ctags = this.checkedCtags.join(",")
          } else {
            tempData.ctags = '';
          }
          updateVideo(tempData).then(() => {
            for (const v of this.list) {
              if (v.id === this.temp.id) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.temp)
                break
              }
            }
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },

    handleDelete(row) {
      deleteVideo(row).then(() => {
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      })
      const index = this.list.indexOf(row)
      this.list.splice(index, 1)
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
