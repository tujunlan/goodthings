package goodthings.dao;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import goodthings.bean.Book;
import goodthings.bean.GoodsCategory;
import goodthings.bean.StringPair;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Map;

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
    public Map<Integer,List<StringPair>> getAllChildTags(int categoryId) {
        String sql = "select tag_id,tag_name,p_tag_id from tag where isdel=0 and p_tag_id<>0 and category_id=? order by p_tag_id,tag_order";
        Map<Integer, List<StringPair>> ret = Maps.newHashMap();
        jdbcTemplate.query(sql, new Object[]{categoryId}, new ResultSetExtractor<List<StringPair>>() {
            @Override
            public List<StringPair> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                int temp = 0;
                while (resultSet.next()) {
                    StringPair sp = new StringPair();
                    sp.setId(resultSet.getInt("tag_id"));
                    sp.setName(resultSet.getString("tag_name"));
                    int ptagId = resultSet.getInt("p_tag_id");
                    if (ptagId != temp) {
                        List<StringPair> samePtag = Lists.newArrayList();
                        samePtag.add(sp);
                        ret.put(ptagId, samePtag);
                        temp = ptagId;
                    }else {
                        ret.get(temp).add(sp);
                    }
                }
                return null;
            }
        });
        return ret;
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

    public int addChildTag(int ptagId, String tagName, int categoryId) {
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
        return keyHolder.getKey().intValue();
    }

    public void deleteTag(int tagId) {
        String sql = "update tag set isdel=1 where tag_id=?";
        jdbcTemplate.update(sql, tagId);
    }

    public int getGoodsParentTag(int categoryId, int goodsId) {
        String sql = "select a.tag_id from goods_tag as a join tag as b on a.tag_id=b.tag_id and b.p_tag_id=0 and a.category_id=? and a.goods_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{categoryId, goodsId}, Integer.class);
    }
    public List<Integer> getGoodsChildTag(int categoryId, int goodsId) {
        String sql = "select a.tag_id from goods_tag as a join tag as b on a.tag_id=b.tag_id and b.p_tag_id<>0 and a.category_id=? and a.goods_id=?";
        return jdbcTemplate.queryForList(sql, new Object[]{categoryId, goodsId}, Integer.class);
    }

    public void updGoodsRef(int goodsId, int userId, int categoryId, String refType, int opr) {
        String tbname = "user_goods_" + refType;
        String sql;
        if (opr == 1) {
            sql = "insert into " + tbname + "(user_id,goods_id,category_id) values(?,?,?)";
        } else {
            sql = "delete from " + tbname + " where user_id=? and goods_id=? and category_id=?";
        }
        jdbcTemplate.update(sql, userId, goodsId, categoryId);
        if ("had".equals(refType)) {
            if (opr == 1) {
                sql = "insert into popular(goods_id,category_id,owner_num) values(?,?,1) ON DUPLICATE KEY UPDATE owner_num=owner_num+1";
            } else {
                sql = "update popular set owner_num=owner_num-1 where goods_id=? and category_id=?";
            }
        }else {
            if (opr == 1) {
                sql = "insert into popular(goods_id,category_id,want_num) values(?,?,1) ON DUPLICATE KEY UPDATE want_num=want_num+1";
            } else {
                sql = "update popular set want_num=want_num-1 where goods_id=? and category_id=?";
            }
        }
        jdbcTemplate.update(sql, goodsId, categoryId);
    }
}
