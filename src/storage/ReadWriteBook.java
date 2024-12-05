package storage;

import model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteBook implements IReadWriteFile {

    private ReadWriteBook() {
    }

    private static ReadWriteBook instance;

    public static ReadWriteBook getInstance() {
        if (instance == null) {
            instance = new ReadWriteBook();
        }
        return instance;
    }


    public List<Book> readBooks() {
        File file = new File("books.txt");
        List<Book> books = new ArrayList<>();
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                books = (List<Book>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return books;
    }

    public void writeBook(List<Book> books) {
        File file = new File("books.txt");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
