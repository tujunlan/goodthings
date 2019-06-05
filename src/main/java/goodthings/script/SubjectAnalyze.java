package goodthings.script;

import com.vdurmont.emoji.EmojiParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubjectAnalyze {
    public static BasicDataSource dataSource = null;
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/test2");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
        }
        return dataSource.getConnection();
    }
    public static void main(String[] args) throws IOException, SQLException {
        String json = FileUtils.readFileToString(new File("D:\\work\\6\\data_format.txt"));
        JSONArray ja = JSONArray.fromObject(json);
        Connection conn = getConnection();
        String sql = "insert into subject(subject,title,href,content,like_cnt,favorites_cnt,comment_cnt) values(?,?,?,?,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        for (int i = 0; i < ja.size(); i++) {
            JSONObject jb = ja.getJSONObject(i);
            String subject = jb.getString("name");
            JSONArray blogs = jb.getJSONArray("blog");
            for (int k = 0; k < blogs.size(); k++) {
                stm.setString(1, subject);
                stm.setString(2, EmojiParser.removeAllEmojis(blogs.getJSONObject(k).getString("title")));
                stm.setString(3, "https://www.xiaohuasheng.cn" + blogs.getJSONObject(k).getString("href"));
                stm.setString(4, EmojiParser.removeAllEmojis(blogs.getJSONObject(k).getString("content")));
                int like = 0;
                int fav = 0;
                int comment = 0;
                try {
                    like = Integer.parseInt(blogs.getJSONObject(k).getString("like").replace("人赞", "").replace("条回答", ""));
                } catch (NumberFormatException e) {
                }
                try {
                    fav = Integer.parseInt(blogs.getJSONObject(k).getString("favorites").replace("人收藏", ""));
                } catch (NumberFormatException e) {
                }
                try {
                    comment = Integer.parseInt(blogs.getJSONObject(k).getString("comment").replace("条评论", ""));
                } catch (NumberFormatException e) {
                }
                stm.setInt(5, like);
                stm.setInt(6, fav);
                stm.setInt(7, comment);
                try {
                    stm.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(blogs.getJSONObject(k).getString("title"));
                }
            }
        }
    }
}
