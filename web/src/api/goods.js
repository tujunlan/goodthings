import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/goods/tags_goods',
    method: 'get',
    params: {
      tag_ids: 2,
      offset: 0,
      pagesize: 20
    }
  })
}
