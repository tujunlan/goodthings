package goodthings.dao;

import goodthings.bean.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setBook_name(resultSet.getString("book_name"));
        book.setOut_link(resultSet.getString("out_link"));
        book.setPic_link(resultSet.getString("pic_link"));
        book.setAuthor(resultSet.getString("author"));
        book.setPress(resultSet.getString("press"));
        book.setDescription(resultSet.getString("description"));
        book.setIsdel(resultSet.getInt("isdel"));
        book.setAdd_time(resultSet.getString("add_time").substring(0,19));
        try {
            book.setApproval_num(resultSet.getInt("approval_num"));
            book.setOwner_num(resultSet.getInt("owner_num"));
        } catch (SQLException e) {
        }
        return book;
    }
}
