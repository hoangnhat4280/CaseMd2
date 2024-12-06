package model;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private String author;
    private int price;
    private boolean isBorrowed; // Trạng thái mượn sách

    // Constructor
    public Book(int id, String title, String author, int price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.isBorrowed = false; // Mặc định sách chưa được mượn
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    // Phương thức mượn sách
    public void borrow() {
        if (!isBorrowed) {
            this.isBorrowed = true;  // Đánh dấu sách đã được mượn
            System.out.println("Sách đã được mượn thành công.");
        } else {
            System.out.println("Sách này đã được mượn.");
        }
    }

    // Phương thức trả sách
    public void returnBook() {
        if (isBorrowed) {
            this.isBorrowed = false;
            System.out.println("Sách đã được trả thành công.");
        } else {
            System.out.println("Sách chưa được mượn.");
        }
    }

    // In thông tin sách
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", isBorrowed=" + isBorrowed +  // Hiển thị trạng thái mượn
                '}';
    }
}
