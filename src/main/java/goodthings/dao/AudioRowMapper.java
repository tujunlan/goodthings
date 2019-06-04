package goodthings.dao;

import goodthings.bean.Audio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AudioRowMapper implements RowMapper<Audio> {
    @Override
    public Audio mapRow(ResultSet resultSet, int i) throws SQLException {
        Audio audio = new Audio();
        audio.setId(resultSet.getInt("id"));
        audio.setAudio_name(resultSet.getString("audio_name"));
        audio.setOut_link(resultSet.getString("out_link"));
        audio.setPic_link(resultSet.getString("pic_link"));
        audio.setAnnouncer(resultSet.getString("announcer"));
        audio.setDescription(resultSet.getString("description"));
        audio.setIsdel(resultSet.getInt("isdel"));
        audio.setAdd_time(resultSet.getString("add_time").substring(0,19));
        try {
            audio.setApproval_num(resultSet.getInt("approval_num"));
            audio.setOwner_num(resultSet.getInt("owner_num"));
        } catch (SQLException e) {
        }
        return audio;
    }
}
