package goodthings.dao.pojo;

import goodthings.bean.Video;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoRowMapper implements RowMapper<Video> {
    @Override
    public Video mapRow(ResultSet resultSet, int i) throws SQLException {
        Video video = new Video();
        video.setId(resultSet.getInt("id"));
        video.setVideo_name(resultSet.getString("video_name"));
        video.setOut_link(resultSet.getString("out_link"));
        video.setPic_link(resultSet.getString("pic_link"));
        video.setProducer(resultSet.getString("producer"));
        video.setDescription(resultSet.getString("description"));
        video.setIsdel(resultSet.getInt("isdel"));
        video.setAdd_time(resultSet.getString("add_time").substring(0,19));
        try {
            video.setApproval_num(resultSet.getInt("approval_num"));
            video.setOwner_num(resultSet.getInt("owner_num"));
        } catch (SQLException e) {
        }
        return video;
    }
}
