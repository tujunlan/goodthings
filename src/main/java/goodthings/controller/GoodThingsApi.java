package goodthings.controller;

import com.alibaba.fastjson.JSONArray;
import com.hylanda.service.http.webwind.renderer.Renderer;
import goodthings.bean.StringPair;
import goodthings.dao.DaoService;
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
@RequestMapping(value="/data")
public class GoodThingsApi{
    @Autowired
    private DaoService daoService;

    @ApiOperation(value = "获取默认大分类", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品类型", required = true, dataType = "int")})
    @RequestMapping(value = "get_default_tags", method = RequestMethod.POST)
    public String getDefaultTags(String category_id) {
        List<StringPair> defaultTags = daoService.getDefaultTags(category_id);
        return JSONArray.toJSONString(defaultTags);
    }
    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "物品类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "goods_id", value = "物品id", required = true, dataType = "string", example = "1,2,3")})
    @RequestMapping(value = "delete_goods", method = RequestMethod.POST)
    public String delete(String category_id, String goods_id) {

        return null;
    }
}
