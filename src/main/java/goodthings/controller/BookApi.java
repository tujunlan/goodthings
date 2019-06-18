package goodthings.controller;

import com.alibaba.fastjson.JSONObject;
import goodthings.bean.Book;
import goodthings.bean.StringPair;
import goodthings.dao.BookDao;
import goodthings.dao.GoodThingsDao;
import goodthings.service.PictureService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by tujl on 2018/12/14.
 */

@RestController
@RequestMapping(value="/book")
public class BookApi {

    @Autowired
    private BookDao bookDao;

    @ApiOperation(value = "按标签查书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tag_ids", value = "标签ids,多个用逗号分隔", required = true, dataType = "string"),
            @ApiImplicitParam(name = "user_id", value = "用户id", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "tags_books", method = RequestMethod.POST)
    public String getBooksByTags(@NotEmpty String tag_ids, Integer user_id, int page, int limit) {
        int offset = (page - 1) * limit;
        List<Book> books;
        if (user_id!=null && user_id > 0) {
            books = bookDao.searchBooksExcludeHad(tag_ids, user_id, offset, limit);
        } else {
            books = bookDao.searchBooksByTags(tag_ids, offset, limit);
        }
        JSONObject jb = new JSONObject();
        jb.put("items", books);
        return new ControllerResult(20000, jb).toJsonString();
    }

    @ApiOperation(value = "查询所有书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "book_name", value = "书名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ptag", value = "大类", required = false, dataType = "int"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页内数量", required = true, dataType = "int")})
    @RequestMapping(value = "all_books", method = RequestMethod.POST)
    public String getAllBooks(String book_name, String ptag, int page, int limit) {
        int offset = (page - 1) * limit;
        long total = bookDao.getCountBooks(book_name, ptag, "0");
        List<Book> books = bookDao.searchAllBooks(book_name, ptag, "0", offset, limit);
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
        List<Book> books = bookDao.searchMyBooks(user_id, want_had, offset, pagesize);
        JSONObject jb = new JSONObject();
        jb.put("items", books);
        return new ControllerResult(20000, jb).toJsonString();
    }

    @ApiOperation(value = "修改书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "json", required = true, dataType = "string")})
    @RequestMapping(value = "update_book_info", method = RequestMethod.POST)
    public String updateBookInfo(@RequestBody String json) {
        JSONObject params = JSONObject.parseObject(json);
        int id = params.getIntValue("id");
        String book_name = params.getString("book_name");
        String pic_link = params.getString("pic_link");
        String author = params.getString("author");
        String press = params.getString("press");
        String out_link = params.getString("out_link");
        String description = params.getString("description");
        int ptag = params.getIntValue("ptag");
        String ctags = params.getString("ctags");
        bookDao.updateBookInfo(id, book_name, out_link, author, press, description, pic_link);
        bookDao.insertBookTag(id, ptag, ctags);
        return new ControllerResult(20000, "success").toJsonString();
    }

    @ApiOperation(value = "创建书", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "json", required = true, dataType = "string")
    })
    @RequestMapping(value = "create_book_info", method = RequestMethod.POST)
    public String createBookInfo(@RequestBody String json) {
        JSONObject params = JSONObject.parseObject(json);
        String book_name = params.getString("book_name");
        String pic_link = params.getString("pic_link");
        String author = params.getString("author");
        String press = params.getString("press");
        String out_link = params.getString("out_link");
        String description = params.getString("description");
        int ptag = params.getIntValue("ptag");
        String ctags = params.getString("ctags");
        int id = bookDao.insertBookInfo(book_name, out_link, pic_link, author, press, description);
        bookDao.insertBookTag(id, ptag, ctags);
        return new ControllerResult(20000, id).toJsonString();
    }

    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物品id", required = true, dataType = "String")})
    @RequestMapping(value = "delete_book", method = RequestMethod.POST)
    public String delete(String id) {
        bookDao.deleteBook(id);
        return new ControllerResult(20000, id).toJsonString();
    }
}
