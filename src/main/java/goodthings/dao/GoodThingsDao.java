package goodthings.dao;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import goodthings.bean.Book;
import goodthings.bean.GoodsCategory;
import goodthings.bean.StringPair;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodThingsDao {
    private static final Logger logger = LoggerFactory.getLogger(GoodThingsDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    final String booksql = "select a.id,a.book_name,a.out_link,a.pic_link,a.author,a.press,a.desc,a.isdel,a.add_time";
    @Transactional
    public void deleteGoods(GoodsCategory category, List<String> goodsIds) {
        String goodsTable = category.name();
        String ids = Joiner.on(",").join(goodsIds);
        String goodsSql = "update "+goodsTable+" set is_del=1 where id in (" + goodsIds + ")";
        String tagSql = "update goods_tag set is_del=1 where goods_id in (" + goodsIds + ") and category_id=" + category.value();
        String popularSql = "update popular set is_del=1 where goods_id in (" + goodsIds + ") and category_id=" + category.value();
        jdbcTemplate.batchUpdate(goodsSql, tagSql, popularSql);
    }

    public List<StringPair> getDefaultTags(int categoryId) {
        String sql = "select tag_id,tag_name from tag where p_tag_id=0 and category_id=" + categoryId;
        return StringPair.transform(jdbcTemplate.queryForList(sql));
    }

    public List<StringPair> getTagsByParent(int ptagId) {
        String sql = "select tag_id,tag_name from tag where p_tag_id=" + ptagId;
        return StringPair.transform(jdbcTemplate.queryForList(sql));
    }

    public long getCountBooks(String name, String isDel){
        String sql = "select count(1) from book as a where 1=1";
        if (StringUtils.isNotBlank(isDel)) {
            sql += " and a.isdel = '" + isDel + "'";
        }
        if (StringUtils.isNotBlank(name)) {
            sql += " and a.book_name like %" + name + "%";
        }
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
    public List<Book> searchAllBooks(String name, String isDel, int offset, int pageSize) {
        String sql = booksql + " from book as a where 1=1";
        if (StringUtils.isNotBlank(isDel)) {
            sql += " and a.isdel = '" + isDel + "'";
        }
        if (StringUtils.isNotBlank(name)) {
            sql += " and a.book_name like %" + name + "%";
        }
        sql += " limit " + offset + "," + pageSize;
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }

    private List<Integer> getChildTagsId(int tagId) {
        String sql = "select tag_id from tag where p_tag_id=?";
        List<Integer> ids = jdbcTemplate.queryForList(sql, new Object[]{tagId}, Integer.class);
        return ids;
    }
    public List<Book> searchBooksByTags(String tagIds, int offset, int pageSize){
        String query = null;
        List<String> ids = Splitter.on(",").splitToList(tagIds);
        if (ids.size() == 1) {//有可能是父tag
            List<Integer> temp = getChildTagsId(Integer.parseInt(tagIds));
            if (temp != null) {
                query = Joiner.on(",").join(temp) + "," + tagIds;
            }
        }
        if (query == null) {
            query = Joiner.on(",").join(ids);
        }
        String sql = booksql + ",c.owner_num,c.approval_num from book as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.book.value()
                + " join popular as c on b.goods_id=c.goods_id and c.category_id=" + GoodsCategory.book.value()
                + " where b.tag_id in (" + query + ") order by c.approval_num,c.owner_num desc limit " + offset + "," + pageSize;
        logger.info(sql);
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }
    public List<Book> searchBooksExcludeHad(String tagIds,int userId,int offset,int pageSize){
        String sql = booksql + ",c.owner_num,c.approval_num from book as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.book.value()
                + " join popular as c on b.goods_id=c.b.goods_id and c.category_id=" + GoodsCategory.book.value()
                + " left join user_goods as d on a.id=d.goods_id and d.category_id=" + GoodsCategory.book.value() + " d.user_id=" + userId
                + " where b.tag_id in (" + tagIds + ") and d.user_id is null order by c.approval_num,c.owner_num desc limit " + offset + "," + pageSize;
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }

    public List<Book> searchMyBooks(int userId,int wantHad,int offset,int pageSize) {
        String sql = booksql + "  from user_goods as b join book as a on b.category_id=" + GoodsCategory.book.value()
                + " and b.goods_id=a.id and b.user_id=" + userId + " and b.want_had=" + wantHad+" order by b.id limit " + offset + "," + pageSize;
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }

}
