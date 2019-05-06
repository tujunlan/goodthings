package goodthings.dao;

import goodthings.bean.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("id"));
        user.setTel(resultSet.getString("book_name"));
        user.setWechatId(resultSet.getString("out_link"));
        user.setNickName(resultSet.getString("pic_link"));
        user.setPwd(resultSet.getString("author"));
        return user;
    }
}
