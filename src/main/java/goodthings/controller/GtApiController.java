package goodthings.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tujl on 2018/12/14.
 */

@RestController
@RequestMapping(value="/data")     // 通过这里配置使下面的映射都在/users下，可去除
public class GtApiController {


    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "goods_id", value = "物品id", required = true, dataType = "string", example = "1,2,3")})
    @RequestMapping(value = "delete_goods", method = RequestMethod.POST)
    public String delete(String category_id, String goods_id) {
        return null;
    }

}
