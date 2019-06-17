import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/video/tags_videos',
    method: 'post',
    params: params
  })
}
export function getAllVideos(params) {
  return request({
    url: '/video/all_videos',
    method: 'post',
    params: params
  })
}
export function updateVideo(params) {
  return request({
    url: '/video/update_video_info',
    method: 'post',
    data: params
  })
}
export function createVideo(params) {
  return request({
    url: '/video/create_video_info',
    method: 'post',
    data: params
  })
}
export function deleteVideo(params) {
  return request({
    url: '/video/delete_video',
    method: 'post',
    params: params
  })
}
