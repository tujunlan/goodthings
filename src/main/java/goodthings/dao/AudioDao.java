package goodthings.dao;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import goodthings.bean.GoodsCategory;
import goodthings.bean.Audio;
import goodthings.dao.pojo.AudioRowMapper;
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
public class AudioDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GoodThingsDao goodThingsDao;

    final String audiosql = "select DISTINCT a.id,a.audio_name,a.out_link,a.pic_link,a.announcer,a.description,a.isdel,a.add_time";
    private String generateSql(String name, String isDel){
        String sql = "";
        if (StringUtils.isNotBlank(isDel)) {
            sql += " and a.isdel = ?";
        }
        if (StringUtils.isNotBlank(name)) {
            sql += " and a.audio_name like ?";
        }
        return sql;
    }
    public long getCountAudios(String name, String isDel) {
        String sql = "select count(1) from audio as a where 1=1";
        List<String> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(isDel)) {
            list.add(isDel);
        }
        if (StringUtils.isNotBlank(name)) {
            list.add("%"+name+"%");
        }
        sql += generateSql(name, isDel);
        return jdbcTemplate.queryForObject(sql, list.toArray(), Long.class);
    }
    public List<Audio> searchAllAudios(String name, String isDel, int offset, int pageSize) {
        String sql = audiosql + " from audio as a where 1=1";
        List<String> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(isDel)) {
            list.add(isDel);
        }
        if (StringUtils.isNotBlank(name)) {
            list.add("%"+name+"%");
        }
        sql += generateSql(name, isDel);
        sql += " limit " + offset + "," + pageSize;
        return jdbcTemplate.query(sql, list.toArray(),new AudioRowMapper());
    }

    public List<Audio> searchAudiosByTags(String tagIds, int offset, int pageSize){
        String query = null;
        List<String> ids = Splitter.on(",").splitToList(tagIds);
        if (ids.size() == 1) {//有可能是父tag
            List<Integer> temp = goodThingsDao.getChildTagsId(Integer.parseInt(tagIds));
            if (temp != null) {
                query = Joiner.on(",").join(temp) + "," + tagIds;
            }
        }
        if (query == null) {
            query = Joiner.on(",").join(ids);
        }
        String sql = audiosql + ",c.owner_num,c.approval_num from audio as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.audio.value()
                + " left join popular as c on b.goods_id=c.goods_id and c.category_id=" + GoodsCategory.audio.value()
                + " where a.isdel=0 and b.tag_id in (" + query + ") order by c.approval_num desc,c.owner_num desc limit " + offset + "," + pageSize;
        return (List<Audio>) jdbcTemplate.query(sql, new AudioRowMapper());
    }
    public List<Audio> searchAudiosExcludeHad(String tagIds,int userId,int offset,int pageSize){
        String sql = audiosql + ",c.owner_num,c.approval_num from audio as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.audio.value()
                + " join popular as c on b.goods_id=c.b.goods_id and c.category_id=" + GoodsCategory.audio.value()
                + " left join user_goods as d on a.id=d.goods_id and d.category_id=" + GoodsCategory.audio.value() + " d.user_id=" + userId
                + " where a.isdel=0 and b.tag_id in (" + tagIds + ") and d.user_id is null order by c.approval_num,c.owner_num desc limit " + offset + "," + pageSize;
        return (List<Audio>) jdbcTemplate.query(sql, new AudioRowMapper());
    }

    public List<Audio> searchMyAudios(int userId,int wantHad,int offset,int pageSize) {
        String sql = audiosql + "  from user_goods as b join audio as a on b.category_id=" + GoodsCategory.audio.value()
                + " and b.goods_id=a.id and b.user_id=" + userId + " and b.want_had=" + wantHad+" order by b.id limit " + offset + "," + pageSize;
        return (List<Audio>) jdbcTemplate.query(sql, new AudioRowMapper());
    }

    public void updateAudioInfo(int audio_id, String audio_name, String out_link, String announcer, String description,String pic_link) {
        String sql = "update audio as a set a.audio_name=?,a.out_link=?,a.announcer=?,a.description=?,a.pic_link=? where a.id=?";
        jdbcTemplate.update(sql, new Object[]{audio_name, out_link, announcer, description, pic_link, audio_id});
    }
    public int insertAudioInfo(String audio_name, String out_link,String pic_link, String announcer, String description) {
        String sql = "insert into audio (audio_name,out_link,pic_link,announcer,description) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                //设置返回的主键字段名
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, audio_name);
                ps.setString(2, out_link);
                ps.setString(3, pic_link);
                ps.setString(4, announcer);
                ps.setString(5, description);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Transactional
    public void insertAudioTag(int audio_id, int ptag_id, String ctag_ids) {
        String delSql = "delete from goods_tag where goods_id=? and category_id=" + GoodsCategory.audio.value();
        jdbcTemplate.update(delSql, audio_id);
        String sql = "insert into goods_tag(goods_id,category_id,tag_id) values(?,?,?)";
        jdbcTemplate.update(sql, audio_id, GoodsCategory.audio.value(), ptag_id);
        List<String> ctagIds = Splitter.on(",").omitEmptyStrings().splitToList(ctag_ids);
        for (String id : ctagIds) {
            jdbcTemplate.update(sql, audio_id, GoodsCategory.audio.value(), id);
        }
    }
    public void deleteAudio(String ids) {
        String goodsSql = "update audio set isdel=1 where id =" + ids;
        jdbcTemplate.update(goodsSql);
    }
}
