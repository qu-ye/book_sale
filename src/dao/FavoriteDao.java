package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Book;


public class FavoriteDao {

  
    public boolean isFavorite(Connection con, int readerId, int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM favorite WHERE reader_id = ? AND book_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, readerId);
            pstmt.setInt(2, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

 
    public int addFavorite(Connection con, int readerId, int bookId) throws SQLException {
        String sql = "INSERT INTO favorite (reader_id, book_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, readerId);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate();
        }
    }

    public int removeFavorite(Connection con, int readerId, int bookId) throws SQLException {
        String sql = "DELETE FROM favorite WHERE reader_id = ? AND book_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, readerId);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate();
        }
    }

   
    public List<Book> getFavoritesByReaderId(Connection con, int readerId) throws SQLException {
        List<Book> favoriteBooks = new ArrayList<>();
        // This query joins favorite and book tables to get book details
        String sql = "SELECT b.* FROM book b JOIN favorite f ON b.book_id = f.book_id WHERE f.reader_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, readerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setBook_id(rs.getInt("book_id"));
                    book.setBook_name(rs.getString("book_name"));
                    book.setBook_writer(rs.getString("book_writer"));
                    book.setBook_price(rs.getDouble("book_price"));
                    book.setBook_discount(rs.getDouble("book_discount"));
                    book.setBook_cover(rs.getString("book_cover"));
                    favoriteBooks.add(book);
                }
            }
        }
        return favoriteBooks;
    }
} 