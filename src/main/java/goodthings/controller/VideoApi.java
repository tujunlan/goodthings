package goodthings.controller;

import com.alibaba.fastjson.JSONObject;
import goodthings.bean.Video;
import goodthings.dao.VideoDao;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by tujl on 2018/12/14.
 */

@RestController
@RequestMapping(value="/video")
public class VideoApi {

    @Autowired
    private VideoDao videoDao;

    @ApiOperation(value = "按标签查视频", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tag_ids", value = "标签ids,多个用逗号分隔", required = true, dataType = "string"),
            @ApiImplicitParam(name = "user_id", value = "用户id", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "tags_videos", method = RequestMethod.POST)
    public String getVideosByTags(@NotEmpty String tag_ids, Integer user_id, int page, int limit) {
        int offset = (page - 1) * limit;
        List<Video> videos;
        if (user_id!=null && user_id > 0) {
            videos = videoDao.searchVideosExcludeHad(tag_ids, user_id, offset, limit);
        } else {
            videos = videoDao.searchVideosByTags(tag_ids, offset, limit);
        }
        JSONObject jb = new JSONObject();
        jb.put("items", videos);
        return new ControllerResult(20000, jb).toJsonString();
    }
    @ApiOperation(value = "查询所有视频", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "video_name", value = "视频名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "all_videos", method = RequestMethod.POST)
    public String getAllVideos(String video_name, int page, int limit) {
        int offset = (page - 1) * limit;
        long total = videoDao.getCountVideos(video_name, "0");
        List<Video> videos = videoDao.searchAllVideos(video_name, "0", offset, limit);
        JSONObject jb = new JSONObject();
        jb.put("total", total);
        jb.put("items", videos);
        return new ControllerResult(20000, jb).toJsonString();
    }
    @ApiOperation(value = "查询所有与我相关的视频", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "want_had", value = "想要的传0，已有的传1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "offset", value = "起点位置", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pagesize", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "tags_videos_referme", method = RequestMethod.POST)
    public String getGoodsByTags(int user_id, int want_had, int offset, int pagesize) {
        List<Video> videos = videoDao.searchMyVideos(user_id, want_had, offset, pagesize);
        JSONObject jb = new JSONObject();
        jb.put("items", videos);
        return new ControllerResult(20000, jb).toJsonString();
    }

    @ApiOperation(value = "修改视频", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "video_name", value = "视频名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pic_link", value = "图片链接", required = true, dataType = "String"),
            @ApiImplicitParam(name = "out_link", value = "外链", required = true, dataType = "String"),
            @ApiImplicitParam(name = "producer", value = "出品方", required = true, dataType = "String"),
            @ApiImplicitParam(name = "description", value = "简介", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ptag", value = "大标签id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "ctags", value = "子标签id", required = true, dataType = "String")})
    @RequestMapping(value = "update_video_info", method = RequestMethod.POST)
    public String updateVideoInfo(int id, String video_name, String pic_link, String producer, String out_link, String description,int ptag,String ctags) {
        videoDao.updateVideoInfo(id, video_name, out_link, producer, description, pic_link);
        videoDao.insertVideoTag(id, ptag, ctags);
        return new ControllerResult(20000, "success").toJsonString();
    }

    @ApiOperation(value = "创建视频", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "video_name", value = "视频名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pic_link", value = "图片链接", required = true, dataType = "String"),
            @ApiImplicitParam(name = "out_link", value = "外链", required = true, dataType = "String"),
            @ApiImplicitParam(name = "producer", value = "出品方", required = true, dataType = "String"),
            @ApiImplicitParam(name = "description", value = "简介", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ptag", value = "大标签id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "ctags", value = "子标签id", required = true, dataType = "String")
    })
    @RequestMapping(value = "create_video_info", method = RequestMethod.POST)
    public String createVideoInfo(String video_name, String pic_link, String out_link, String producer, String description,int ptag,String ctags) {
        int id = videoDao.insertVideoInfo(video_name, out_link, pic_link, producer, description);
        videoDao.insertVideoTag(id, ptag, ctags);
        return new ControllerResult(20000, id).toJsonString();
    }

    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物品id", required = true, dataType = "String")})
    @RequestMapping(value = "delete_video", method = RequestMethod.POST)
    public String delete(String id) {
        videoDao.deleteVideo(id);
        return new ControllerResult(20000, id).toJsonString();
    }
}
