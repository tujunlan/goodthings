package goodthings.controller;

import com.alibaba.fastjson.JSONObject;
import goodthings.bean.StringPair;
import goodthings.dao.GoodThingsDao;
import goodthings.service.PictureService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by tujl on 2018/12/14.
 */

@RestController
@RequestMapping(value="/goods")
public class GoodThingsApi{
    @Autowired
    private GoodThingsDao goodThingsDao;
    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "获取所有物品类型", notes = "")
    @RequestMapping(value = "category", method = RequestMethod.POST)
    public String getCategory() {
        List<StringPair> category = goodThingsDao.getCategory();
        JSONObject jb = new JSONObject();
        jb.put("items", category);
        return new ControllerResult(20000, jb).toJsonString();
    }

    @ApiOperation(value = "获取默认大分类", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品种类id", required = true, dataType = "int")})
    @RequestMapping(value = "default_tags", method = RequestMethod.POST)
    public String getDefaultTags(int category_id) {
        List<StringPair> defaultTags = goodThingsDao.getDefaultTags(category_id);
        JSONObject jb = new JSONObject();
        jb.put("items", defaultTags);
        String ret = new ControllerResult(20000, jb).toJsonString();
        return ret;
    }

    @ApiOperation(value = "获取所有子标签", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品种类id", required = true, dataType = "int")})
    @RequestMapping(value = "all_children_tags", method = RequestMethod.POST)
    public String getAllChildrenTags(int category_id) {
        Map<Integer,List<StringPair>> childrenTags = goodThingsDao.getAllChildTags(category_id);
        String ret = new ControllerResult(20000, childrenTags).toJsonString();
        return ret;
    }

    @ApiOperation(value = "获取子标签", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "p_tag_id", value = "父标签id", required = true, dataType = "int")})
    @RequestMapping(value = "children_tags", method = RequestMethod.POST)
    public String getChildrenTags(int p_tag_id) {
        List<StringPair> childrenTags = goodThingsDao.getTagsByParent(p_tag_id);
        JSONObject jb = new JSONObject();
        jb.put("items", childrenTags);
        return new ControllerResult(20000, jb).toJsonString();
    }
    @ApiOperation(value = "删除子标签", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tag_id", value = "tag_id", required = true, dataType = "int")})
    @RequestMapping(value = "delete_tag", method = RequestMethod.POST)
    public String deleteTag(int tag_id) {
        goodThingsDao.deleteTag(tag_id);
        return new ControllerResult(20000, "success").toJsonString();
    }
    @ApiOperation(value = "新建子标签", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tag_name", value = "tag_name", required = true, dataType = "string")})
    @RequestMapping(value = "create_tag", method = RequestMethod.POST)
    public String createTag(int ptag_id,String tag_name,int category_id) {
        int newTagId = goodThingsDao.addChildTag(ptag_id, tag_name, category_id);
        return new ControllerResult(20000, newTagId).toJsonString();
    }
    @ApiOperation(value = "上传图片", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "书名", required = true, dataType = "int", paramType = "form")})
    @RequestMapping(value = "upload_image", method = RequestMethod.POST)
    public String upload_image(@ApiParam(value = "图片", required = true)
                               @RequestParam(value = "file") MultipartFile file,
                               @RequestParam(value = "category_id")int category_id) {
        try {
            String uploadPth = pictureService.uploadPicture(file, category_id);
            if (uploadPth != null) {
                return new ControllerResult(20000, uploadPth).toJsonString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ControllerResult(50000, "图片上传失败").toJsonString();
    }

    @ApiOperation(value = "获取物品的标签", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "goods_id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "category_id", value = "category_id", required = true, dataType = "int")
    })
    @RequestMapping(value = "get_goods_tag", method = RequestMethod.POST)
    public String getGoodsTags(int category_id,int goods_id) {
        int ptagId = goodThingsDao.getGoodsParentTag(category_id, goods_id);
        List<Integer> cagId = goodThingsDao.getGoodsChildTag(category_id, goods_id);
        JSONObject jb = new JSONObject();
        jb.put("ptag", ptagId);
        jb.put("ctags", cagId);
        return new ControllerResult(20000, jb).toJsonString();
    }
}
