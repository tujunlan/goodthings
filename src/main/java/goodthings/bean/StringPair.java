package goodthings.bean;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class StringPair {
    private String id;
    private String name;

    public static List<StringPair> transform(List<Map<String, Object>> list) {
        if(list==null){
            return null;
        }
        List<StringPair> ret = Lists.newArrayList();
        for(Map<String,Object> map:list){
            StringPair sp=new StringPair();
            for(String k:map.keySet()){
                if (k.indexOf("id") != -1) {
                    sp.id = map.get(k).toString();
                }else {
                    sp.name = map.get(k).toString();
                }
            }
            ret.add(sp);
        }
        return ret;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
