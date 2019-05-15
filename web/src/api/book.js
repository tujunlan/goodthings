import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/goods/tags_books',
    method: 'get',
    params: params
  })
}
export function getAllBooks(params) {
  return request({
    url: '/goods/all_books',
    method: 'get',
    params: params
  })
}
