package storage;

import model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReadWriteFile implements IReadWriteFile {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id,title,author,price,isBorrowed";

    @Override
    public List<Book> readBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("books.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                if (values.length == 5) {
                    int id = Integer.parseInt(values[0]);
                    String title = values[1];
                    String author = values[2];
                    int price = Integer.parseInt(values[3]);
                    boolean isBorrowed = Boolean.parseBoolean(values[4]);
                    Book book = new Book(id, title, author, price);
                    book.setBorrowed(isBorrowed);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void writeBook(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.csv"))) {
            bw.write(FILE_HEADER);
            bw.write(NEW_LINE_SEPARATOR);
            for (Book book : books) {
                bw.write(book.getId() + COMMA_DELIMITER +
                        book.getTitle() + COMMA_DELIMITER +
                        book.getAuthor() + COMMA_DELIMITER +
                        book.getPrice() + COMMA_DELIMITER +
                        book.isBorrowed());
                bw.write(NEW_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
