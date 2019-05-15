import request from '@/utils/request'

export function getParentTags(params) {
  return request({
    url: '/goods/default_tags',
    method: 'get',
    params: params
  })
}
export function getChildTags(params) {
  return request({
    url: '/goods/children_tags',
    method: 'get',
    params: params
  })
}
