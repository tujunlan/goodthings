package goodthings.dao;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import goodthings.bean.Book;
import goodthings.bean.GoodsCategory;
import goodthings.dao.pojo.BookRowMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class BookDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GoodThingsDao goodThingsDao;

    final String booksql = "select DISTINCT a.id,a.book_name,a.out_link,a.pic_link,a.author,a.press,a.description,a.isdel,a.add_time";
    private String generateSql(String name, String ptag, String isDel,List<Object> args){
        String sql = "";
        if (StringUtils.isNotBlank(isDel)) {
            sql += " and a.isdel = ?";
            args.add(isDel);
        }
        if (StringUtils.isNotBlank(name)) {
            sql += " and a.book_name like ?";
            args.add("%" + name + "%");
        }
        if (StringUtils.isNotBlank(ptag)) {
            sql += " and b.tag_id=?";
            args.add(ptag);
        }
        return sql;
    }

    public long getCountBooks(String name, String ptag, String isDel) {
        String sql;
        if (StringUtils.isNotBlank(ptag)) {
            sql = "select count(1) from book as a join goods_tag as b on b.category_id=" + GoodsCategory.book.value() + " and a.id=b.goods_id where 1=1";
        } else {
            sql = "select count(1) from book as a where 1=1";
        }
        List<Object> list = Lists.newArrayList();
        sql += generateSql(name, ptag, isDel, list);
        return jdbcTemplate.queryForObject(sql, list.toArray(), Long.class);
    }
    public List<Book> searchAllBooks(String name,String ptag, String isDel, int offset, int pageSize) {
        String sql;
        if (StringUtils.isNotBlank(ptag)) {
            sql = booksql + " from book as a join goods_tag as b on b.category_id=" + GoodsCategory.book.value() + " and a.id=b.goods_id";
        } else {
            sql = booksql + " from book as a where 1=1";
        }
        List<Object> list = Lists.newArrayList();
        sql += generateSql(name, ptag, isDel, list);
        sql += " order by a.id limit " + offset + "," + pageSize;
        return jdbcTemplate.query(sql, list.toArray(),new BookRowMapper());
    }

    private String genBooksByTagsSql(String tagIds){
        String query = null;
        List<String> ids = Splitter.on(",").splitToList(tagIds);
        if (ids.size() == 1) {//有可能是父tag
            List<Integer> temp = goodThingsDao.getChildTagsId(Integer.parseInt(tagIds));
            if (temp != null && !temp.isEmpty()) {
                query = Joiner.on(",").join(temp) + "," + tagIds;
            }
        }
        if (query == null) {
            query = Joiner.on(",").join(ids);
        }
        String sql = " from book as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.book.value()
                + " left join popular as c on b.goods_id=c.goods_id and c.category_id=" + GoodsCategory.book.value()
                + " where a.isdel=0 and b.tag_id in (" + query + ")";
        return sql;
    }
    public long getTotalBooksByTags(String tagIds){
        String sql = "select distinct a.id" + genBooksByTagsSql(tagIds);
        return jdbcTemplate.queryForList(sql).size();
    }
    public List<Book> searchBooksByTags(String tagIds, int offset, int pageSize){
        String sql = booksql + ",c.owner_num,c.approval_num"+genBooksByTagsSql(tagIds)+" order by c.approval_num desc,c.owner_num desc limit " + offset + "," + pageSize;
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }
    private String genBooksExcludeHadSql(String tagIds, int userId){
        String sql = " from book as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.book.value()
                + " join popular as c on b.goods_id=c.b.goods_id and c.category_id=" + GoodsCategory.book.value()
                + " left join user_goods as d on a.id=d.goods_id and d.category_id=" + GoodsCategory.book.value() + " d.user_id=" + userId
                + " where a.isdel=0 and b.tag_id in (" + tagIds + ") and d.user_id is null";
        return sql;
    }
    public long getTotalBooksExcludeHad(String tagIds,int userId){
        String sql = "select distinct a.id" + genBooksExcludeHadSql(tagIds, userId);
        return jdbcTemplate.queryForList(sql).size();
    }
    public List<Book> searchBooksExcludeHad(String tagIds,int userId,int offset,int pageSize){
        String sql = booksql + ",c.owner_num,c.approval_num" + genBooksExcludeHadSql(tagIds, userId) + "  order by c.approval_num,c.owner_num desc limit " + offset + "," + pageSize;
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }

    public List<Book> searchMyBooks(int userId,String wantHad,int offset,int pageSize) {
        String tbname = "user_goods_" + wantHad;
        String sql = booksql + " from book as a join " + tbname + " as b on b.category_id=" + GoodsCategory.book.value()
                + " and b.goods_id=a.id and b.user_id=" + userId + " order by a.id limit " + offset + "," + pageSize;
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }

    public void updateBookInfo(int book_id, String book_name, String out_link, String author, String press, String description,String pic_link) {
        String sql = "update book as a set a.book_name=?,a.out_link=?,a.author=?,a.description=?,a.press=?,a.pic_link=? where a.id=?";
        jdbcTemplate.update(sql, new Object[]{book_name, out_link, author, description, press, pic_link, book_id});
    }
    public int insertBookInfo(String book_name, String out_link,String pic_link, String author, String press, String description) {
        String sql = "insert into book (book_name,out_link,pic_link,author,press,description) values(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                //设置返回的主键字段名
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, book_name);
                ps.setString(2, out_link);
                ps.setString(3, pic_link);
                ps.setString(4, author);
                ps.setString(5, press);
                ps.setString(6, description);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Transactional
    public void insertBookTag(int book_id, int ptag_id, String ctag_ids) {
        String delSql = "delete from goods_tag where goods_id=? and category_id=" + GoodsCategory.book.value();
        jdbcTemplate.update(delSql, book_id);
        String sql = "insert into goods_tag(goods_id,category_id,tag_id) values(?,?,?)";
        jdbcTemplate.update(sql, book_id, GoodsCategory.book.value(), ptag_id);
        List<String> ctagIds = Splitter.on(",").omitEmptyStrings().splitToList(ctag_ids);
        for (String id : ctagIds) {
            jdbcTemplate.update(sql, book_id, GoodsCategory.book.value(), id);
        }
    }
    public void deleteBook(String ids) {
        String goodsSql = "update book set isdel=1 where id =" + ids;
        jdbcTemplate.update(goodsSql);
    }
}
