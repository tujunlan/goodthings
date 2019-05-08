package goodthings.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hylanda.service.http.webwind.renderer.Renderer;
import goodthings.bean.Book;
import goodthings.bean.StringPair;
import goodthings.bean.User;
import goodthings.dao.GoodThingsService;
import goodthings.dao.UserService;
import goodthings.dto.UserLoginDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tujl on 2018/12/14.
 */

@RestController
@RequestMapping(value="/user")
public class UserApi extends ControllerSupport{
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录获取用户信息", notes = "例如{\"username\": \"admin\", \"password\": \"111111\"}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginInfo", value = "帐号", required = true, dataType = "UserLoginDto")})
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody UserLoginDto loginInfo) {
        User user = userService.loginUser(loginInfo.getUsername(), loginInfo.getPassword());
        if (user != null) {
            JSONObject jb = new JSONObject();
            jb.put("uid",user.getUserId());
            jb.put("avatar", user.getAvatar());
            jb.put("name", user.getNickName());
            return new ControllerResult(20000, jb).toJsonString();
        }else {
            return new ControllerResult(60204, "Account and password are incorrect.").toJsonString();
        }
    }

}
