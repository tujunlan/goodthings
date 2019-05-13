package goodthings.controller;

import com.alibaba.fastjson.JSONObject;
import goodthings.bean.User;
import goodthings.dao.UserDao;
import goodthings.dto.UserLoginDto;
import goodthings.login.JavaWebToken;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by tujl on 2018/12/14.
 */

@RestController
@RequestMapping(value="/user")
public class UserApi extends ControllerSupport{
    @Autowired
    private UserDao userDao;

    @ApiOperation(value = "用户登录", notes = "例如{\"username\": \"admin\", \"password\": \"111111\"}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginInfo", value = "帐号", required = true, dataType = "UserLoginDto")})
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody UserLoginDto loginInfo) {
        User user = userDao.loginUser(loginInfo.getUsername(), loginInfo.getPassword());
        if (user != null) {
            JSONObject jb = new JSONObject();
            jb.put("uid",user.getUserId());
            jb.put("avatar", user.getAvatar());
            jb.put("name", user.getNickName());
            String token = JavaWebToken.createJavaWebToken(jb);
            JSONObject data = new JSONObject();
            data.put("token", token);
            return new ControllerResult(20000, data).toJsonString();
        }else {
            return new ControllerResult(60204, "Account and password are incorrect.").toJsonString();
        }
    }
    @ApiOperation(value = "获取登录用户信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String")})
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(@RequestParam String token) {
        if (StringUtils.isNotBlank(token)) {
            Set<Map.Entry<String, Object>> set = JavaWebToken.verifyJavaWebToken(token).entrySet();
            JSONObject jb = new JSONObject();
            Iterator<Map.Entry<String, Object>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> map = it.next();
                jb.put(map.getKey(), map.getValue());
            }
            return new ControllerResult(20000, jb).toJsonString();
        }
        return new ControllerResult(50008, "Login failed, unable to get user details.").toJsonString();
    }
    @ApiOperation(value = "退出登录", notes = "")
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public String logout() {
        return new ControllerResult(20000, "success").toJsonString();
    }
}
