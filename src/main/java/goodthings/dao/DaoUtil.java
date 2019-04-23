package goodthings.dao;

import com.google.common.base.Joiner;
import goodthings.bean.Book;
import goodthings.bean.GoodsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DaoUtil {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void deleteGoods(GoodsCategory category, List<String> goodsIds) {
        String goodsTable = category.name();
        String ids = Joiner.on(",").join(goodsIds);
        String goodsSql = "update "+goodsTable+" set is_del=1 where id in (" + goodsIds + ")";
        String tagSql = "update goods_tag set is_del=1 where goods_id in (" + goodsIds + ") and category_id=" + category.value();
        String popularSql = "update popular set is_del=1 where goods_id in (" + goodsIds + ") and category_id=" + category.value();
        jdbcTemplate.batchUpdate(goodsSql, tagSql, popularSql);
    }

    public List<Book> searchAllBooks(String isDel) {
        String sql = "select id,book_name,out_link,pic_link,author,press,desc,caution,add_time from book where isdel = '" + isDel + "'";
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }
    public List<Book> searchBooks(String tagIds,int offset,int pageSize){
        String sql = "select a.id,a.book_name,a.out_link,a.pic_link,a.author,a.press,a.desc,a.caution,a.add_time,c.owner_num,c.approval_num from book as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.book.value()
                + " join popular as c on b.goods_id=c.b.goods_id and c.category_id=" + GoodsCategory.book.value()
                + " where b.tag_id in (" + tagIds + ") order by c.approval_num,c.owner_num desc limit "+offset+","+pageSize;
        return (List<Book>) jdbcTemplate.query(sql, new BookRowMapper());
    }
    public void addBook(){

    }
}
