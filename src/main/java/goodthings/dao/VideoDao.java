package goodthings.dao;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import goodthings.bean.Video;
import goodthings.bean.GoodsCategory;
import goodthings.dao.pojo.VideoRowMapper;
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
public class VideoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GoodThingsDao goodThingsDao;

    final String videosql = "select DISTINCT a.id,a.video_name,a.out_link,a.pic_link,a.producer,a.description,a.isdel,a.add_time";
    private String generateSql(String name, String isDel){
        String sql = "";
        if (StringUtils.isNotBlank(isDel)) {
            sql += " and a.isdel = ?";
        }
        if (StringUtils.isNotBlank(name)) {
            sql += " and a.video_name like ?";
        }
        return sql;
    }
    public long getCountVideos(String name, String isDel) {
        String sql = "select count(1) from video as a where 1=1";
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
    public List<Video> searchAllVideos(String name, String isDel, int offset, int pageSize) {
        String sql = videosql + " from video as a where 1=1";
        List<String> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(isDel)) {
            list.add(isDel);
        }
        if (StringUtils.isNotBlank(name)) {
            list.add("%"+name+"%");
        }
        sql += generateSql(name, isDel);
        sql += " limit " + offset + "," + pageSize;
        return jdbcTemplate.query(sql, list.toArray(),new VideoRowMapper());
    }

    public List<Video> searchVideosByTags(String tagIds, int offset, int pageSize){
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
        String sql = videosql + ",c.owner_num,c.approval_num from video as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.video.value()
                + " left join popular as c on b.goods_id=c.goods_id and c.category_id=" + GoodsCategory.video.value()
                + " where a.isdel=0 and b.tag_id in (" + query + ") order by c.approval_num desc,c.owner_num desc limit " + offset + "," + pageSize;
        return (List<Video>) jdbcTemplate.query(sql, new VideoRowMapper());
    }
    public List<Video> searchVideosExcludeHad(String tagIds,int userId,int offset,int pageSize){
        String sql = videosql + ",c.owner_num,c.approval_num from video as a"
                + " join goods_tag as b on a.id=b.goods_id and b.category_id=" + GoodsCategory.video.value()
                + " join popular as c on b.goods_id=c.b.goods_id and c.category_id=" + GoodsCategory.video.value()
                + " left join user_goods as d on a.id=d.goods_id and d.category_id=" + GoodsCategory.video.value() + " d.user_id=" + userId
                + " where a.isdel=0 and b.tag_id in (" + tagIds + ") and d.user_id is null order by c.approval_num,c.owner_num desc limit " + offset + "," + pageSize;
        return (List<Video>) jdbcTemplate.query(sql, new VideoRowMapper());
    }

    public List<Video> searchMyVideos(int userId,int wantHad,int offset,int pageSize) {
        String sql = videosql + "  from user_goods as b join video as a on b.category_id=" + GoodsCategory.video.value()
                + " and b.goods_id=a.id and b.user_id=" + userId + " and b.want_had=" + wantHad+" order by b.id limit " + offset + "," + pageSize;
        return (List<Video>) jdbcTemplate.query(sql, new VideoRowMapper());
    }

    public void updateVideoInfo(int video_id, String video_name, String out_link, String producer, String description,String pic_link) {
        String sql = "update video as a set a.video_name=?,a.out_link=?,a.producer=?,a.description=?,a.pic_link=? where a.id=?";
        jdbcTemplate.update(sql, new Object[]{video_name, out_link, producer, description, pic_link, video_id});
    }
    public int insertVideoInfo(String video_name, String out_link,String pic_link, String producer, String description) {
        String sql = "insert into video (video_name,out_link,pic_link,producer,description) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                //设置返回的主键字段名
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, video_name);
                ps.setString(2, out_link);
                ps.setString(3, pic_link);
                ps.setString(4, producer);
                ps.setString(5, description);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Transactional
    public void insertVideoTag(int video_id, int ptag_id, String ctag_ids) {
        String delSql = "delete from goods_tag where goods_id=? and category_id=" + GoodsCategory.video.value();
        jdbcTemplate.update(delSql, video_id);
        String sql = "insert into goods_tag(goods_id,category_id,tag_id) values(?,?,?)";
        jdbcTemplate.update(sql, video_id, GoodsCategory.video.value(), ptag_id);
        List<String> ctagIds = Splitter.on(",").omitEmptyStrings().splitToList(ctag_ids);
        for (String id : ctagIds) {
            jdbcTemplate.update(sql, video_id, GoodsCategory.video.value(), id);
        }
    }
    public void deleteVideo(String ids) {
        String goodsSql = "update video set isdel=1 where id =" + ids;
        jdbcTemplate.update(goodsSql);
    }
}
