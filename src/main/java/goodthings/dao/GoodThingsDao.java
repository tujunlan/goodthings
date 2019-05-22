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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class GoodThingsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<StringPair> getCategory(){
        String sql = "select category_id,category_name from category where enable=1";
        return StringPair.transform(jdbcTemplate.queryForList(sql));
    }
    public List<StringPair> getDefaultTags(int categoryId) {
        String sql = "select tag_id,tag_name from tag where isdel=0 and p_tag_id=0 and category_id=" + categoryId;
        return StringPair.transform(jdbcTemplate.queryForList(sql));
    }

    public List<StringPair> getTagsByParent(int ptagId) {
        String sql = "select tag_id,tag_name from tag where isdel=0 and p_tag_id=" + ptagId;
        return StringPair.transform(jdbcTemplate.queryForList(sql));
    }

    public List<Integer> getChildTagsId(int tagId) {
        String sql = "select tag_id from tag where isdel=0 and p_tag_id=?";
        List<Integer> ids = jdbcTemplate.queryForList(sql, new Object[]{tagId}, Integer.class);
        return ids;
    }

    public long addChildTag(int ptagId, String tagName, int categoryId) {
        String sql = "insert into tag(tag_name,category_id,p_tag_id) values(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                //设置返回的主键字段名
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tagName);
                ps.setInt(2, categoryId);
                ps.setInt(3, ptagId);
                return ps;
            }
        }, keyHolder);
        return (long)keyHolder.getKey();
    }

    public void deleteTag(int tagId) {
        String sql = "update tag set isdel=1 where tag_id=?";
        jdbcTemplate.update(sql, tagId);
    }
}
