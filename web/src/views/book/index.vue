<!--<template>
  <div class="app-container">
    <ui class="goods-list">
      <li class="goods-item" v-for="(item, index) in list">
        <div class="goods-img">
          <img v-bind:src="item.pic_link" v-bind:alt="item.out_link">
          &lt;!&ndash;div class="flag" v-if="item.ishot">热</div&ndash;&gt;
        </div>
        <div class="goods-info">
          <p class="goods-title">{{ item.book_name }}</p>
          <div class="goods-price">
            <span>{{ item.author }} · {{ item.press }}</span>
          </div>
          <span class="des">{{ item.owner_num }}人有 {{ item.approval_num }}人推荐</span>
          <span class="save" @click="iHad(item)">+</span>
          <span class="save" @click="iWant(item)">+</span>
        </div>
      </li>
    </ui>
  </div>
</template>-->
<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="Title">
        <template slot-scope="scope">
          {{ scope.row.book_name }}
        </template>
      </el-table-column>
      <el-table-column label="Author" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
  import {getList} from '@/api/goods'

  export default {
    data() {
      return {
        list: null,
        listLoading: true
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getList().then(response => {
          this.list = response.data.items
          this.listLoading = false
        })
      }
    }
  }
</script>

<style>

  .goods-list {
    padding-top: 8px;
    height: 513px;
    overflow-y: scroll;
  }

  .goods-item {
    display: flex;
    margin-bottom: 8px;
    padding: 10px 6px;
    min-height: 62px;
    background: #fff;
  }

  .goods-img {
    position: relative;
    margin-right: 4%;
    display: block;
    width: 16%;
  }

  .goods-img img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
  }

  .goods-item .flag {
    position: absolute;
    top: 0;
    left: 0;
    width: 20px;
    height: 20px;
    font-size: 12px;
    color: #fff;
    text-align: center;
    line-height: 20px;
    background-color: #FC5951;
    border-radius: 50%;
  }

  .goods-info {
    position: relative;
    width: 80%;
  }

  .goods-title {
    width: 80%;
    height: 38px;
    color: #363636;
    line-height: 1.4;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
  }

  .goods-price {
    margin-top: 6px;
    line-height: 1;
  }

  .goods-price span {
    font-size: 15px;
    color: #7a45e5;
  }

  .des {
    font-size: 12px;
    color: #888;
  }

  .save {
    position: absolute;
    right: 10px;
    bottom: 2px;
    width: 32px;
    height: 22px;
    background-color: #7a45e5;
    font-size: 16px;
    line-height: 19px;
    text-align: center;
    color: #fff;
    border-radius: 12px;
    overflow: hidden;
  }

  .empty-states {
    padding-top: 60px;
    font-size: 18px;
    color: #AEB0B7;
    text-align: center;
  }

  .cart-list .goods-info {
    width: 68%;
  }
</style>
