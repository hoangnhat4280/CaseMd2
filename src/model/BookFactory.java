package model;

public class BookFactory {
    public static Book createBook(int id, String title, String author, int price) {
        return new Book(id, title, author, price);
    }
}
