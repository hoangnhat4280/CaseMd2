package controller.book;

import model.Book;
import storage.IReadWriteFile;
import storage.ReadWriteBook;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BookController implements IBookController {
    static IReadWriteFile iReadWriteFile = ReadWriteBook.getInstance();
    public static List<Book> books = iReadWriteFile.readBooks();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public List<Book> list() {
        return books;
    }

    @Override
    public void add(Book book) {
        books.add(book);
        iReadWriteFile.writeBook(books);
    }

    public void update(int id, Book updatedBook) {
        Optional<Book> bookOpt = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPrice(updatedBook.getPrice());
            iReadWriteFile.writeBook(books);
        }
    }

    public void delete(int id) {
        books.removeIf(book -> book.getId() == id);
        iReadWriteFile.writeBook(books);
    }

    public void showBooks() {
        if (books.isEmpty()) {
            System.out.println("Không có sách nào.");
        } else {
            System.out.println("Danh sách sách:");
            books.forEach(System.out::println);
        }
    }

    public Book getBookFromUser() {
        System.out.print("Mời bạn nhập vào id: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Tên sách: ");
        String title = scanner.nextLine();

        System.out.print("Tác giả: ");
        String author = scanner.nextLine();

        System.out.print("Giá sách: ");
        int price = scanner.nextInt();

        return new Book(id, title, author, price);
    }

    public void addBook() {
        Book book = getBookFromUser();
        add(book);
        System.out.println("Sách đã được thêm.");
    }

    public void updateBook() {
        System.out.print("Nhập id sách cần sửa: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Đọc ký tự xuống dòng

        System.out.println("Nhập thông tin sách mới:");
        Book updatedBook = getBookFromUser();
        update(id, updatedBook);
        System.out.println("Sách đã được sửa.");
    }

    public void deleteBook() {
        System.out.print("Nhập id sách cần xóa: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        delete(id);
        System.out.println("Sách đã được xóa.");
    }
}
