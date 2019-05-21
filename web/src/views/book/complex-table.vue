<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.book_name" placeholder="书名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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
      <el-table-column lable="序号" prop="id" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column lable="图片" prop="image" width="110px" align="center">
        <template slot-scope="scope">
          <a :href="scope.row.out_link" target="_blank">
            <img :src="scope.row.pic_link"  min-width="70" height="70" />
          </a>
        </template>
      </el-table-column>
      <el-table-column lable="书名" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.book_name }}</span>
        </template>
      </el-table-column>
      <el-table-column lable="作者" width="110px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column lable="出版社" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.row.press }}</span>
        </template>
      </el-table-column>
      <el-table-column lable="简单描述" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.row.desc }}</span>
        </template>
      </el-table-column>
      <el-table-column lable="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button  size="mini" type="danger" @click="handleModifyStatus(row,'deleted')">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item lable="图片" prop="pic_link">
          <el-upload
            ref="imgUpload"
            list-type="picture-card"
            :auto-upload="false"
            accept="image/jpg,image/png"
            action="/goods/upload_image"
            :data="{category_id:1}"
            :limit="1"
            :before-upload="handleBeforeUpload"
            :on-preview="handlePictureCardPreview"
            :on-success="handlePicUploadSuccess">
            <i class="el-icon-upload"></i>
          </el-upload>
        </el-form-item>
        <el-form-item lable="书名" prop="book_name">
          <el-input v-model="temp.book_name" />
        </el-form-item>
        <el-form-item lable="作者" prop="author">
          <el-input v-model="temp.author" />
        </el-form-item>
        <el-form-item lable="出版社" prop="press">
          <el-input v-model="temp.press" />
        </el-form-item>
        <el-form-item lable="外链地址" prop="out_link">
          <el-input v-model="temp.out_link" />
        </el-form-item>
        <el-form-item lable="简介">
          <el-input v-model="temp.desc" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
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

<script>
import { getAllBooks, createBook, updateBook } from '@/api/book'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    typeFilter(type) {
      return ageTypeKeyValue[type]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        book_name: undefined,
        type: undefined
      },
      temp: {
        id: undefined,
        book_name: '',
        author: 1,
        press: '',
        out_link: '',
        desc: ''
      },
      file: {
        dialogImageUrl: '',
        imgUrl:''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      rules: {
        title: [{ required: true, message: 'title is required', trigger: 'blur' }],
        type: [{ required: true, message: 'type is required', trigger: 'change' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      getAllBooks(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        book_name: 1,
        author: '',
        press: '',
        out_link: '',
        desc: ''
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
          this.$refs.imgUpload.submit();
          this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          this.temp.author = 'vue-element-admin'
          createBook(this.temp).then(() => {
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          updateBook(tempData).then(() => {
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
    // 图片选择后 保存在 diaLogForm.imgBroadcastList  对象中
    handleBeforeUpload (file) {
      if(!(file.type === 'image/png' || file.type === 'image/jpg')) {
        this.$notify.warning({
          title: '警告',
          message: '请上传格式为image/png, image/jpg的图片'
        })
      }
      let size = file.size / 1024 / 1024
      if(size > 1) {
        this.$notify.warning({
          title: '警告',
          message: '图片大小必须小于1M'
        })
      }
    },
    handlePicUploadSuccess:function(res){
      if(res.status==200){
        this.file.imgUrl=res.data.imgUrl;
      }
    },
    handlePictureCardPreview(file) {
      this.file.dialogImageUrl = file.url;
    },
    handleDelete(row) {
      this.$notify({
        title: '成功',
        message: '删除成功',
        type: 'success',
        duration: 2000
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
