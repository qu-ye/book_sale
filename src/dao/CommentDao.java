package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Comment;

public class CommentDao {

  
    public List<Comment> getCommentsByBookId(Connection con, int bookId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        // Join with reader table to get reader's nickname
        String sql = "SELECT c.*, r.reader_nickname FROM comment c JOIN reader r ON c.reader_id = r.reader_id WHERE c.book_id = ? ORDER BY c.create_time DESC";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Comment c = new Comment();
                    c.setCommentId(rs.getInt("comment_id"));
                    c.setBookId(rs.getInt("book_id"));
                    c.setReaderId(rs.getInt("reader_id"));
                    c.setContent(rs.getString("content"));
                    c.setRating(rs.getInt("rating"));
                    c.setCreateTime(rs.getTimestamp("create_time"));
                    c.setReaderName(rs.getString("reader_nickname")); // Get nickname
                    comments.add(c);
                }
            }
        }
        return comments;
    }


    public int addComment(Connection con, Comment comment) throws SQLException {
        String sql = "INSERT INTO comment (book_id, reader_id, content, rating) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, comment.getBookId());
            pstmt.setInt(2, comment.getReaderId());
            pstmt.setString(3, comment.getContent());
            pstmt.setInt(4, comment.getRating());
            return pstmt.executeUpdate();
        }
    }

  
    public double getAverageRating(Connection con, int bookId) throws SQLException {
        String sql = "SELECT AVG(rating) FROM comment WHERE book_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0; // Return 0 if no ratings
    }
} 