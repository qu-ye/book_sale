package model;

import java.sql.Timestamp;


public class Favorite {
    // 属性: favorite_id
    // 类型: int
    // 作用: 存储收藏记录的唯一标识符。
    private int favorite_id;
    // 属性: reader_id
    // 类型: int
    // 作用: 存储收藏该图书的读者的ID。
    private int reader_id;
    // 属性: book_id
    // 类型: int
    // 作用: 存储被收藏图书的ID。
    private int book_id;
    // 属性: favorite_time
    // 类型: Timestamp
    // 作用: 存储收藏该图书的时间。
    private Timestamp favorite_time;
    // 属性: update_time
    // 类型: Timestamp
    // 作用: 存储收藏记录的最后更新时间。
    private Timestamp update_time;

    public int getFavorite_id() {
        return favorite_id;
    }

   
    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    
    public int getReader_id() {
        return reader_id;
    }

    
    public void setReader_id(int reader_id) {
        this.reader_id = reader_id;
    }

    
    public int getBook_id() {
        return book_id;
    }

    
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    
    public Timestamp getFavorite_time() {
        return favorite_time;
    }

   
    public void setFavorite_time(Timestamp favorite_time) {
        this.favorite_time = favorite_time;
    }

  
    public Timestamp getUpdate_time() {
        return update_time;
    }

   
    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
} 