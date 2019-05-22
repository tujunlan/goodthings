<template>
  <div class="app-container">
    <div>
      <el-radio-group v-model="category" @change="categoryChange">
        <el-radio-button :label="item.id" v-for="item in categorylist">{{item.name}}</el-radio-button>
      </el-radio-group>
    </div>
    <div>
      <el-radio-group v-model="ptag" @change="ptagChange">
        <el-radio-button :label="item.id" v-for="item in ptaglist">{{item.name}}</el-radio-button>
      </el-radio-group>
    </div>
    <div>
      <el-tag
        :key="tag.id"
        v-for="tag in ctaglist"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)">
        {{tag.name}}
      </el-tag>
      <el-input
        class="input-new-tag"
        v-if="inputVisible"
        v-model="inputValue"
        ref="saveTagInput"
        size="small"
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
      >
      </el-input>
      <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
    </div>
  </div>
</template>
<style>
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
<script>
  import {getCategory,getParentTags,getChildTags,deleteChildTag} from '@/api/goods'

  export default {
  data() {
    return {
      category: 1,
      categorylist: null,
      ptag: null,
      ptagQuery: {
        category_id: this.category
      },
      ptaglist: null,
      ctagQuery: {
        p_tag_id: this.ptag
      },
      ctaglist: null
    };
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      getCategory().then(response => {
        this.categorylist = response.data.items
      })
      getParentTags(this.ptagQuery).then(response => {
        this.ptaglist = response.data.items
        this.ptag = this.ptaglist[0].id
        getChildTags(this.ctagQuery).then(response => {
          this.ctaglist = response.data.items
        })
      })
    },
    categoryChange(item) {
      this.category = item.value;
    },
    handleClose(tag) {
      deleteChildTag(tag.id)
      this.ctaglist.splice(this.ctaglist.indexOf(tag), 1)
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue
      if (inputValue) {
        this.dynamicTags.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
    }
  }
}
</script>

