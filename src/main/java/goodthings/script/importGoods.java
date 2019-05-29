package goodthings.script;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class importGoods {
    public static BasicDataSource dataSource = null;
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/goodthings?characterEncoding=utf8");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
        }
        return dataSource.getConnection();
    }
    public static void main(String[] args) throws IOException, SQLException {
        String json = FileUtils.readFileToString(new File("E:\\goodthings\\new\\11-14岁书单.txt"));
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        JSONArray ja = JSONArray.fromObject(json);
        Connection conn = getConnection();
        String sql = "insert into book(book_name,out_link,pic_link,author,press,add_time) values(?,?,?,?,?,?)";
        String tagSql = "insert into goods_tag(goods_id,category_id,tag_id) values(?,1,4)";
        String popularSql = "insert into popular(goods_id,category_id,owner_num,approval_num) value(?,1,?,?)";
        PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmTag = conn.prepareStatement(tagSql);
        PreparedStatement stmPopular = conn.prepareStatement(popularSql);
        boolean b = false;
        ResultSet rs = null;
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
            if (StringUtils.isNotBlank(authorpress)) {
                if (authorpress.lastIndexOf("/") == -1) {
/*                    System.out.println(name+" "+authorpress);
                    b = true;
                    continue;*/
                    if (authorpress.indexOf("地图班") != -1 || authorpress.indexOf("出版社") != -1) {
                        press = authorpress;
                    } else {
                        author = authorpress;
                    }
                }else {
                    author = authorpress.substring(0, authorpress.lastIndexOf("/")).trim();
                    press = authorpress.substring(authorpress.lastIndexOf("/") + 1).trim();
                }
            }
            int approval_num = jb.getInt("approval_num");
            stm.setString(1,name);
            stm.setString(2,link);
            stm.setString(3,photo);
            stm.setString(4,author);
            stm.setString(5,press);
            stm.setString(6,now);
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            long bid;
            if (rs.next()) {
                bid = (long) rs.getObject(1);
                stmTag.setLong(1,bid);
                stmPopular.setLong(1, bid);
                stmPopular.setInt(2, owner_num);
                stmPopular.setInt(3, approval_num);
                stmTag.addBatch();
                stmPopular.addBatch();
            }
        }
        stmTag.executeBatch();
        stmPopular.executeBatch();
        if (b) {
            System.exit(1);
        }
    }
}
