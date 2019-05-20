package goodthings.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PictureDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertPicture(int categoryId, String picLink, byte[] image) {
        String sql = "insert into (category_id,pic_link,image) values(?,?,?)";
        jdbcTemplate.update(sql, categoryId, picLink, image);
    }
}
