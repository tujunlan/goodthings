package goodthings.script;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;

public class PretreatData {
    public static void main(String[] args) throws IOException {
        String json = FileUtils.readFileToString(new File("E:\\goodthings\\3-6岁书单.txt"));
        JSONArray ja = JSONArray.fromObject(json);
        for (int i = 0; i <ja.size() ; i++) {
            JSONObject jb = ja.getJSONObject(i);
            String link = jb.getString("link");
            String photo = jb.getString("photo");
            String name = jb.getString("name");
            String info = jb.getString("info");
            int owner_num = 0;
            if (StringUtils.isNotBlank(info)) {
                String[] s = info.split(" · ");
                owner_num = Integer.parseInt(s[0].replace("人有", ""));
            }
            String authorpress = jb.getString("press");
            String author = "";
            String press = "";
            try {
                author = authorpress.substring(0, authorpress.lastIndexOf("/")).trim();
                press = authorpress.substring(authorpress.lastIndexOf("/") + 1).trim();
            } catch (Exception e) {
                System.out.println(name + "   " + authorpress);
            }
        }
    }
}
