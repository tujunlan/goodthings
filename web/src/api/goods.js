import request from '@/utils/request'

export function getParentTags(params) {
  return request({
    url: '/goods/default_tags',
    method: 'get',
    params: {
      category_id: 1
    }
  })
}
export function getChildTags(params) {
  return request({
    url: '/goods/children_tags',
    method: 'get',
    params: {
      p_tag_id: 2
    }
  })
}
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
