import request from '@/utils/request'

export function getParentTags(params) {
  return request({
    url: '/goods/default_tags',
    method: 'post',
    params: params
  })
}
export function getChildTags(params) {
  return request({
    url: '/goods/children_tags',
    method: 'post',
    params: params
  })
}
export function getCategory() {
  return request({
    url: '/goods/category',
    method: 'post'
  })
}
export function deleteChildTag(params) {
  return request({
    url: '/goods/delete_tag',
    method: 'post',
    params: params
  })
}
export function addChildTag(params) {
  return request({
    url: '/goods/create_tag',
    method: 'post',
    params: params
  })
}
export function getGoodsTag(params) {
  return request({
    url: '/goods/get_goods_tag',
    method: 'post',
    params: params
  })
}
export function getAllTags(params) {
  return request({
    url: '/goods/all_children_tags',
    method: 'post',
    params: params
  })
}
