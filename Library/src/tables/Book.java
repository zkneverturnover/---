package tables;

import java.sql.Date;

public class Book {
    private String author;   // 图书作者
    private String id;       // 图书编号
    private String sid;      // 书架编号
    private String title;    // 书名
    private int now_amount;  // 当前数量
    private int total;       // 总量

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public int getNow_amount() {
        return now_amount;
    }
    public void setNow_amount(int now_amount) {
        this.now_amount = now_amount;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}
