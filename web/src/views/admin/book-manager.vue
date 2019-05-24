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
      <el-table-column label="书名" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.book_name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="作者" width="110px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出版社" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.row.press }}</span>
        </template>
      </el-table-column>
      <el-table-column label="简单描述" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
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
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="标签">
          <div style="margin-bottom:10px; ">
            <el-radio-group v-model="temp.ptag" @change="ptagChange">
              <el-radio-button :label="item.id" v-for="item in ptaglist">{{item.name}}</el-radio-button>
            </el-radio-group>
          </div>
          <div>
            <el-checkbox-group v-model="temp.ctags" size="medium">
              <el-checkbox-button v-for="tag in ctaglist" :label="tag.id" :key="tag.id">{{tag.name}}</el-checkbox-button>
            </el-checkbox-group>
          </div>
        </el-form-item>
        <el-form-item label="图片" prop="pic_link">
          <el-upload
            ref="imgUpload"
            action="/dev-api/goods/upload_image"
            :show-file-list="false"
            :data="{category_id:1}"
            :before-upload="handleBeforeUpload"
            :on-success="handlePicUploadSuccess">
            <img v-if="temp.pic_link" :src="temp.pic_link" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="书名" prop="book_name">
          <el-input v-model="temp.book_name" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="temp.author" />
        </el-form-item>
        <el-form-item label="出版社" prop="press">
          <el-input v-model="temp.press" />
        </el-form-item>
        <el-form-item label="外链地址" prop="out_link">
          <el-input v-model="temp.out_link" />
        </el-form-item>
        <el-form-item label="简介">
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
import { getAllBooks, createBook, updateBook, deleteBook } from '@/api/book'
import {getParentTags,getChildTags} from '@/api/goods'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
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
      ptagQuery: {
        category_id: 1
      },
      ptaglist: null,
      ctagQuery: {
        p_tag_id: null
      },
      ctaglist: null,
      temp: {
        id: undefined,
        pic_link: '',
        book_name: '',
        author: 1,
        press: '',
        out_link: '',
        description: '',
        ptag: '',
        ctags: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      rules: {
        book_name: [{ required: true, message: 'book_name is required', trigger: 'blur' }],
        author: [{ required: true, message: 'author is required', trigger: 'change' }],
        press: [{ required: true, message: 'author is required', trigger: 'change' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
    this.fetchTag()
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
    fetchTag(){
      getParentTags(this.ptagQuery).then(response => {
        this.ptaglist = response.data.items
      })
    },
    ptagChange(item) {
      this.temp.ctags = ''
      this.ctagQuery.p_tag_id = this.temp.ptag
      getChildTags(this.ctagQuery).then(response => {
        this.ctaglist = response.data.items
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        book_name: '',
        author: '',
        press: '',
        out_link: '',
        pic_link: '',
        description: '',
        ptag: '',
        ctags: ''
      }
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
          if (this.temp.pic_link && this.temp.ptag) {
            createBook(this.temp).then(response => {
              this.temp.id = response.data
              this.list.unshift(this.temp)
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
            })
          } else {
            this.$notify.error({
              title: '图片缺失',
              message: '请上传图片'
            })
          }
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
        if (valid && this.temp.ptag) {
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

    handleDelete(row) {
      deleteBook(row).then(() => {
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
