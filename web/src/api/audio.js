import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/audio/tags_audios',
    method: 'post',
    params: params
  })
}
export function getAllAudios(params) {
  return request({
    url: '/audio/all_audios',
    method: 'post',
    params: params
  })
}
export function updateAudio(params) {
  return request({
    url: '/audio/update_audio_info',
    method: 'post',
    params: params
  })
}
export function createAudio(params) {
  return request({
    url: '/audio/create_audio_info',
    method: 'post',
    params: params
  })
}
export function deleteAudio(params) {
  return request({
    url: '/audio/delete_audio',
    method: 'post',
    params: params
  })
}
