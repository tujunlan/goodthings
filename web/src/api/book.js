import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/goods/tags_books',
    method: 'post',
    params: params
  })
}
export function getAllBooks(params) {
  return request({
    url: '/goods/all_books',
    method: 'post',
    params: params
  })
}
export function updateBook(params) {
  return request({
    url: '/goods/update_book_info',
    method: 'post',
    params: params
  })
}
