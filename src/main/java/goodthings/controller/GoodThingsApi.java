package goodthings.controller;

import com.alibaba.fastjson.JSONObject;
import goodthings.bean.Book;
import goodthings.bean.StringPair;
import goodthings.dao.GoodThingsDao;
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
@RequestMapping(value="/goods")
public class GoodThingsApi{
    @Autowired
    private GoodThingsDao goodThingsDao;

    @ApiOperation(value = "获取默认大分类", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品种类id", required = true, dataType = "int")})
    @RequestMapping(value = "default_tags", method = RequestMethod.POST)
    public String getDefaultTags(int category_id) {
        List<StringPair> defaultTags = goodThingsDao.getDefaultTags(category_id);
        JSONObject jb = new JSONObject();
        jb.put("items", defaultTags);
        return new ControllerResult(20000, jb).toJsonString();
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

    @ApiOperation(value = "按标签查书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tag_ids", value = "标签ids,多个用逗号分隔", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_id", value = "用户id",  dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "tags_books", method = RequestMethod.POST)
    public String getBooksByTags(@NotEmpty String tag_ids, Integer user_id, int page, int limit) {
        int offset = (page - 1) * limit;
        List<Book> books;
        if (user_id!=null && user_id > 0) {
            books = goodThingsDao.searchBooksExcludeHad(tag_ids, user_id, offset, limit);
        } else {
            books = goodThingsDao.searchBooksByTags(tag_ids, offset, limit);
        }
        JSONObject jb = new JSONObject();
        jb.put("items", books);
        return new ControllerResult(20000, jb).toJsonString();
    }
    @ApiOperation(value = "查询所有书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "book_name", value = "书名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "all_books", method = RequestMethod.POST)
    public String getAllBooks(String book_name, int page, int limit) {
        int offset = (page - 1) * limit;
        long total = goodThingsDao.getCountBooks(book_name, "0");
        List<Book> books = goodThingsDao.searchAllBooks(book_name, "0", offset, limit);
        JSONObject jb = new JSONObject();
        jb.put("total", total);
        jb.put("items", books);
        return new ControllerResult(20000, jb).toJsonString();
    }
    @ApiOperation(value = "查询所有与我相关的书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "want_had", value = "想要的传0，已有的传1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "offset", value = "起点位置", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pagesize", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "tags_books_referme", method = RequestMethod.POST)
    public String getGoodsByTags(int user_id, int want_had, int offset, int pagesize) {
        List<Book> books = goodThingsDao.searchMyBooks(user_id, want_had, offset, pagesize);
        JSONObject jb = new JSONObject();
        jb.put("items", books);
        return new ControllerResult(20000, jb).toJsonString();
    }

    @ApiOperation(value = "修改书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "book_name", value = "书名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "out_link", value = "外链", required = true, dataType = "String"),
            @ApiImplicitParam(name = "author", value = "作者", required = true, dataType = "String"),
            @ApiImplicitParam(name = "press", value = "出版社", required = true, dataType = "String"),
            @ApiImplicitParam(name = "desc", value = "作者", required = true, dataType = "String")})
    @RequestMapping(value = "update_book_info", method = RequestMethod.POST)
    public String updateBookInfo(int id, String book_name, String author, String press, String out_link, String desc) {
        goodThingsDao.updateBookInfo(id, book_name, out_link, author, press, desc);
        return new ControllerResult(20000, "success").toJsonString();
    }
    @ApiOperation(value = "创建书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "book_id", value = "book_id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "book_name", value = "书名", required = true,  dataType = "String"),
            @ApiImplicitParam(name = "out_link", value = "外链", required = true,  dataType = "String"),
            @ApiImplicitParam(name = "author", value = "作者", required = true,  dataType = "String"),
            @ApiImplicitParam(name = "press", value = "出版社", required = true,  dataType = "String"),
            @ApiImplicitParam(name = "desc", value = "作者", required = true,  dataType = "String")})
    @RequestMapping(value = "create_book_info", method = RequestMethod.POST)
    public String createBookInfo(int book_id,String book_name,String out_link,String author, String press, String desc) {
        goodThingsDao.updateBookInfo(book_id, book_name, out_link, author, press, desc);
        return new ControllerResult(20000, "success").toJsonString();
    }

    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "goods_id", value = "物品id", required = true, dataType = "String", example = "1,2,3")})
    @RequestMapping(value = "delete_goods", method = RequestMethod.POST)
    public String delete(String category_id, String goods_id) {

        return null;
    }
}
