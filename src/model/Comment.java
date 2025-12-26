package model;

import java.sql.Timestamp;


public class Comment {
    // 属性: commentId
    // 类型: int
    // 作用: 存储评论的唯一标识符。
    private int commentId;
    // 属性: bookId
    // 类型: int
    // 作用: 存储评论所属图书的ID。
    private int bookId;
    // 属性: readerId
    // 类型: int
    // 作用: 存储发表评论的读者的ID。
    private int readerId;
    // 属性: content
    // 类型: String
    // 作用: 存储评论的具体文字内容。
    private String content;
    // 属性: rating
    // 类型: int
    // 作用: 存储评论的评分（例如1-5星）。
    private int rating;
    // 属性: createTime
    // 类型: Timestamp
    // 作用: 存储评论创建的时间戳。
    private Timestamp createTime;
    // 属性: readerName
    // 类型: String
    // 作用: 存储读者的姓名，用于在UI中显示。
    private String readerName; // For displaying reader's name in UI
    
   
    public Comment() {
    }
    
   
    public Comment(int commentId, int bookId, int readerId, String content, int rating, Timestamp createTime) {
        this.commentId = commentId;
        this.bookId = bookId;
        this.readerId = readerId;
        this.content = content;
        this.rating = rating;
        this.createTime = createTime;
    }
    
   
    public int getCommentId() {
        return commentId;
    }

  
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

  
    public int getBookId() {
        return bookId;
    }

    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

  
    public int getReaderId() {
        return readerId;
    }

   
    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

 
    public String getContent() {
        return content;
    }

  
    public void setContent(String content) {
        this.content = content;
    }

   
    public int getRating() {
        return rating;
    }

   
    public void setRating(int rating) {
        this.rating = rating;
    }

   
    public Timestamp getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    
    public String getReaderName() {
        return readerName;
    }

  
    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }
} 