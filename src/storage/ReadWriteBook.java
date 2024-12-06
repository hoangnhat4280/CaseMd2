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

    @Override
    public List<Book> readBooks() {
        File file = new File("books.txt");
        List<Book> books = new ArrayList<>();
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                books = (List<Book>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading the books file: " + e.getMessage());
            }
        }
        return books;
    }

    @Override
    public void writeBook(List<Book> books) {
        File file = new File("books.txt");
        try {
            // Kiểm tra nếu file đã tồn tại thì xóa nó để ghi lại
            if (file.exists()) {
                file.delete();
            }
            // Tạo lại file mới và ghi danh sách sách vào file
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(books);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to books file", e);
        }
    }
}
