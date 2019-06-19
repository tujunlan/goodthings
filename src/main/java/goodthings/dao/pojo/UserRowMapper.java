package goodthings.dao.pojo;

import goodthings.bean.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setTel(resultSet.getString("tel"));
        user.setWechatId(resultSet.getString("wechat_id"));
        user.setNickName(resultSet.getString("nick_name"));
        user.setPwd(resultSet.getString("pwd"));
        user.setAvatar(resultSet.getString("avatar"));
        return user;
    }
}
