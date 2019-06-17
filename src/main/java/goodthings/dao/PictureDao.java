package goodthings.dao;

import goodthings.util.http.HTTPHelper;
import goodthings.util.http.HttpSend;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;

@Repository
public class PictureDao {
    @Value("${upload.url}")
    private String uploadUrl;
    @Value("${upload.filepath}")
    private String uploadPath;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertPicture(int categoryId, String picLink, byte[] image) {
        String sql = "insert into picture(category_id,pic_link,image) values(?,?,?)";
        jdbcTemplate.update(sql, categoryId, picLink, image);
    }
    private void save(InputStream is, String strFileName) {
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            FileOutputStream fos = new FileOutputStream(strFileName);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void download() throws IOException {
        String sql = "select pic_link from book where pic_link like '%img.xiaohuasheng.cn%'";
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        HTTPHelper http = null;
        for (String link : list) {
            String picName = link.replace("?imageView2/1/w/160/h/240", "");
            picName = picName.substring(picName.lastIndexOf("/") + 1);
            http = new HTTPHelper(link, "utf8", false);
            InputStream is = http.get();
            save(is, uploadPath + File.separator + "book" + File.separator + picName);
            http.close();
        }
    }
    private byte[] InputStream2ByteArray(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

        return data;
    }
    private byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
    public void initPic() throws IOException {
        String dirStr = uploadPath + File.separator + "book";
        File dir = new File(dirStr);
        String[] list = dir.list();
        for (int i = 0; i < list.length; i++) {
            insertPicture(1, uploadUrl + "book/" + list[i], InputStream2ByteArray(FilenameUtils.concat(dirStr, list[i])));
        }
    }

    public void updatePicLink(){
        String dirStr = uploadPath + File.separator + "book";
        File dir = new File(dirStr);
        String[] list = dir.list();
        for (int i = 0; i < list.length; i++) {
            String url = uploadUrl + "book/" + list[i];
            String sql = "update book set pic_link='" + url + "' where pic_link like '%" + list[i] + "%'";
            jdbcTemplate.update(sql);
        }
    }
}
