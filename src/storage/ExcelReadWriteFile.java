
package storage;

import model.Book;

import java.util.Collections;
import java.util.List;

public class ExcelReadWriteFile implements IReadWriteFile {

    @Override
    public List<Book> readBooks() {
        return Collections.emptyList();
    }

    @Override
    public void writeBook(List<Book> books) {
    }
}
