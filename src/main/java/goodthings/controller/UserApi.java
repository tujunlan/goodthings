package goodthings.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hylanda.service.http.webwind.renderer.Renderer;
import goodthings.bean.Book;
import goodthings.bean.StringPair;
import goodthings.bean.User;
import goodthings.dao.GoodThingsService;
import goodthings.dao.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tujl on 2018/12/14.
 */

@RestController
@RequestMapping(value="/users")
public class UserApi extends ControllerSupport{
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录获取用户信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "帐号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")})
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Renderer login(String username, String password) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            JSONObject jb = new JSONObject();
            jb.put("uid",user.getUserId());
            jb.put("avatar", user.getUserId());
            jb.put("name", user.getNickName());
            return this.createRenderer(new ControllerResult(20000, jb));
        }else {
            return this.createRenderer(new ControllerResult(60204, "Account and password are incorrect."));
        }
    }

}
