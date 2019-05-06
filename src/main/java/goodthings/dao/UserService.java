package goodthings.dao;

import goodthings.bean.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserInfo(Map<String,String> userIdentity) {
        String sql = "select user_id,tel,wechat_id,nick_name,pwd from user where 1=1";
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

    public User loginUser(String tel, String nickName, String pwd) {
        String sql = "select user_id,tel,wechat_id,nick_name,pwd from user where pwd=?";
        String param;
        if (StringUtils.isNotBlank(tel)) {
            sql += " and tel=?";
            param = tel;
        } else if (StringUtils.isNotBlank(nickName)) {
            sql += " and nick_name=?";
            param = nickName;
        } else {
            return null;
        }
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{pwd, param}, new UserRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }
}
