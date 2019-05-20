package goodthings.dao;

import goodthings.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Repository
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserInfo(Map<String,String> userIdentity) {
        String sql = "select user_id,tel,wechat_id,nick_name,pwd,avatar from user where 1=1";
        String param;
        if(userIdentity.containsKey("tel")){
            sql += " and tel=?";
            param = userIdentity.get("tel");
        }else if(userIdentity.containsKey("wechat_id")){
            sql += " and wechat_id=?";
            param = userIdentity.get("wechat_id");
        }else if(userIdentity.containsKey("nick_name")){
            sql += " and nick_name=?";
            param = userIdentity.get("nick_name");
        }else {
            return null;
        }
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{param}, new UserRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public User loginUser(String username,String password) {
        String sql = "select user_id,tel,wechat_id,nick_name,pwd,avatar from user where pwd=? and (tel=? or nick_name=?)";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{password, username,username}, new UserRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }
}
