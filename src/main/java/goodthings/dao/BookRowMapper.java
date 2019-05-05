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
        book.setDesc(resultSet.getString("desc"));
        book.setPress(resultSet.getString("caution"));
        book.setAdd_time(resultSet.getString("add_time"));
        return book;
    }
}
