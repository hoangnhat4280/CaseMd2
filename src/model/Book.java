package model;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private String author;
    private int price;
    private boolean isBorrowed;

    public Book(int id, String title, String author, int price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.isBorrowed = false; // Mặc định sách chưa được mượn
    }

    // Getter và Setter cho các thuộc tính
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

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public void borrow() {
        if (!isBorrowed) {
            this.isBorrowed = true;
            System.out.println("Sách đã được mượn thành công.");
        } else {
            System.out.println("Sách này đã được mượn.");
        }
    }

    public void returnBook() {
        if (isBorrowed) {
            this.isBorrowed = false;
            System.out.println("Sách đã được trả thành công.");
        } else {
            System.out.println("Sách chưa được mượn.");
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", isBorrowed=" + isBorrowed +
                '}';
    }
}