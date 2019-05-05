package goodthings.controller;

import com.alibaba.fastjson.JSONArray;
import goodthings.bean.Book;
import goodthings.bean.StringPair;
import goodthings.dao.DaoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tujl on 2018/12/14.
 */

@RestController
@RequestMapping(value="/goods")
public class GoodThingsApi{
    @Autowired
    private DaoService daoService;

    @ApiOperation(value = "获取默认大分类", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品种类id", required = true, dataType = "int")})
    @RequestMapping(value = "default_tags", method = RequestMethod.GET)
    public String getDefaultTags(int category_id) {
        List<StringPair> defaultTags = daoService.getDefaultTags(category_id);
        return JSONArray.toJSONString(defaultTags);
    }

    @ApiOperation(value = "获取子标签", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "p_tag_id", value = "父标签id", required = true, dataType = "int")})
    @RequestMapping(value = "children_tags", method = RequestMethod.GET)
    public String getChildrenTags(int p_tag_id) {
        List<StringPair> childrenTags = daoService.getTagsByParent(p_tag_id);
        return JSONArray.toJSONString(childrenTags);
    }

    @ApiOperation(value = "按标签查物品", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tag_ids", value = "标签ids,多个用逗号分隔", required = true, dataType = "String"),
            @ApiImplicitParam(name = "offset", value = "起点位置", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pagesize", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "tags_goods", method = RequestMethod.GET)
    public String getGoodsByTags(String tag_ids, int offset, int pagesize) {
        List<Book> books = daoService.searchBooks(tag_ids, offset, pagesize);
        return JSONArray.toJSONString(books);
    }

    @ApiOperation(value = "按标签查物品，排除我有的", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tag_ids", value = "标签ids,多个用逗号分隔", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_id", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "offset", value = "起点位置", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pagesize", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "tags_goods_excludeme", method = RequestMethod.POST)
    public String getGoodsByTags(String tag_ids, int user_id, int offset, int pagesize) {
        List<Book> books = daoService.searchBooksExcludeHad(tag_ids, user_id, offset, pagesize);
        return JSONArray.toJSONString(books);
    }

    @ApiOperation(value = "查询所有与我相关的书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "want_had", value = "想要的传0，已有的传1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "offset", value = "起点位置", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pagesize", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "tags_goods_referme", method = RequestMethod.POST)
    public String getGoodsByTags(int user_id, int want_had, int offset, int pagesize) {
        List<Book> books = daoService.searchMyBooks(user_id, want_had, offset, pagesize);
        return JSONArray.toJSONString(books);
    }

    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "goods_id", value = "物品id", required = true, dataType = "string", example = "1,2,3")})
    @RequestMapping(value = "delete_goods", method = RequestMethod.POST)
    public String delete(String category_id, String goods_id) {

        return null;
    }
}
