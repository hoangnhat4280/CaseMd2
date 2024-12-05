package controller.book;

import model.Book;
import storage.IReadWriteFile;
import storage.ReadWriteBook;

import java.util.List;

public class BookController implements IBookController {
    static IReadWriteFile iReadWriteFile = ReadWriteBook.getInstance();

    public static List<Book> books = iReadWriteFile.readBooks();

    @Override
    public List<Book> list() {
        return books;
    }

    @Override
    public void add(Book book) {
        // Thêm sách mới vào danh sách
        books.add(book);
        // Lưu lại vào file
        iReadWriteFile.writeBook(books);
    }
}
