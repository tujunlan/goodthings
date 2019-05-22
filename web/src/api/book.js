import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/book/tags_books',
    method: 'post',
    params: params
  })
}
export function getAllBooks(params) {
  return request({
    url: '/book/all_books',
    method: 'post',
    params: params
  })
}
export function updateBook(params) {
  return request({
    url: '/book/update_book_info',
    method: 'post',
    params: params
  })
}
export function createBook(params) {
  return request({
    url: '/book/create_book_info',
    method: 'post',
    params: params
  })
}
export function deleteBook(params) {
  return request({
    url: '/book/delete_book',
    method: 'post',
    params: params
  })
}
