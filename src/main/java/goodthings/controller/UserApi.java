package goodthings.controller;

import com.alibaba.fastjson.JSONArray;
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
public class UserApi {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录获取用户信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "电话", required = true, dataType = "String"),
            @ApiImplicitParam(name = "nick_name", value = "昵称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "String")})
    @RequestMapping(value = "default_tags", method = RequestMethod.GET)
    public String login(String tel, String nick_name, String pwd) {
        User user = userService.loginUser(tel, nick_name, pwd);
        if (user != null) {

        }
        return JSONArray.toJSONString(user);
    }

}
