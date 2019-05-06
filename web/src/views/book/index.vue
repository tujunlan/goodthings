<div class="app-container">
  <ul class="goods-list">
    <li class="goods-item" v-for="(item, index) in list">
      <div class="goods-img">
        <img v-bind:src="item.img" v-bind:alt="item.name">
        <div class="flag" v-if="item.ishot">热</div>
      </div>
      <div class="goods-info">
        <p class="goods-title">{{ item.name }}</p>
        <div class="goods-price">
          <span>¥<b>{{ item.price }}</b></span>
        </div>
        <span class="des">{{ item.sales }}人付款</span>
        <span class="save" @click="addToCart(item)">+</span>
      </div>
    </li>
  </ul>
</div>

<script>
import { getList } from '@/api/goods'

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
