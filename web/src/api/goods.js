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
