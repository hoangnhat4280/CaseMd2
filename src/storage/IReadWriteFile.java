
package storage;

import model.Book;

import java.util.List;

public interface IReadWriteFile {
    List<Book> readBooks();
    void writeBook(List<Book> books);
}
