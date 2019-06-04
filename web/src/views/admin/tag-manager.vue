<template>
  <div class="app-container">
    <div style="margin-bottom:10px; ">
      <el-radio-group v-model="category" @change="categoryChange">
        <el-radio-button :label="item.id" v-for="item in categorylist">{{item.name}}</el-radio-button>
      </el-radio-group>
    </div>
    <div style="margin-bottom:10px; ">
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
  import {getCategory,getParentTags,getChildTags,deleteChildTag,addChildTag} from '@/api/goods'

  export default {
  data() {
    return {
      category: null,
      categorylist: null,
      ptag: null,
      ptagQuery: {
        category_id: null
      },
      ptaglist: null,
      ctagQuery: {
        p_tag_id: null
      },
      ctaglist: null,
      ctagParam: {
        ptag_id: null,
        tag_name: null,
        category_id: null
      },
      ctagDelParam: {
        tag_id: null
      },
      inputVisible: false,
      inputValue: null
    };
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      getCategory().then(response => {
        this.categorylist = response.data.items
        this.category = this.categorylist[0].id
        this.ptagQuery.category_id = this.category
        getParentTags(this.ptagQuery).then(response => {
          this.ptaglist = response.data.items
          this.ptag = this.ptaglist[0].id;
          this.ctagQuery.p_tag_id = this.ptag
          getChildTags(this.ctagQuery).then(response => {
            this.ctaglist = response.data.items
          })
        })
      })

    },
    categoryChange(item) {
      this.category = item;
      this.ptagQuery.category_id = this.category
      getParentTags(this.ptagQuery).then(response => {
        this.ptaglist = response.data.items
        this.ptag = this.ptaglist[0].id
        this.ctagQuery.p_tag_id = this.ptag
        getChildTags(this.ctagQuery).then(response => {
          this.ctaglist = response.data.items
        })
      })
    },
    ptagChange(item) {
      this.ptag = item;
      this.ctagQuery.p_tag_id = this.ptag
      getChildTags(this.ctagQuery).then(response => {
        this.ctaglist = response.data.items
      })
    },
    handleClose(tag) {
      this.ctagDelParam.tag_id = tag.id
      deleteChildTag(this.ctagDelParam).then(response => {
        this.ctaglist.splice(this.ctaglist.indexOf(tag), 1)
      })
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
        this.ctagParam.ptag_id = this.ptag
        this.ctagParam.tag_name = this.inputValue
        this.ctagParam.category_id = this.category
        addChildTag(this.ctagParam).then(response => {
          this.ctaglist.push({id:response.data, name: inputValue})
        })
      }
      this.inputVisible = false
      this.inputValue = ''
    }
  }
}
</script>

