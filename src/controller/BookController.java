package controller;


import model.*;

import model.observer.ConsoleNotification;
import model.observer.NotificationService;
import storage.IReadWriteFile;
import storage.CSVReadWriteFile;
import storage.ReadWriteBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BookController implements IBookController {
    static IReadWriteFile binaryFileStorage = ReadWriteBook.getInstance();
    static IReadWriteFile csvFileStorage = new CSVReadWriteFile();

    public static List<Book> books = binaryFileStorage.readBooks();
    private static List<Member> members = new ArrayList<>();
    private static List<LoanRecord> loanRecords = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    private final NotificationService notificationService = new NotificationService();

    public BookController() {
        // Add a default console notification observer
        notificationService.addObserver(new ConsoleNotification());
    }

    @Override
    public List<Book> list() {
        return books;
    }

    @Override
    public void add(Book book) {
        books.add(book);
        binaryFileStorage.writeBook(books);
        csvFileStorage.writeBook(books);
    }

    public void addBook() {
        System.out.print("Mời bạn nhập vào id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Tên sách: ");
        String title = scanner.nextLine();
        System.out.print("Tác giả: ");
        String author = scanner.nextLine();
        System.out.print("Giá sách: ");
        int price = scanner.nextInt();
        scanner.nextLine();

        // Create book using factory
        Book book = BookFactory.createBook(id, title, author, price);
        add(book);
        System.out.println("Sách đã được thêm.");
    }

    public void updateBook() {
        System.out.print("Nhập id sách cần sửa: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nhập tên sách mới: ");
        String title = scanner.nextLine();
        System.out.print("Nhập tác giả mới: ");
        String author = scanner.nextLine();
        System.out.print("Nhập giá mới: ");
        int price = scanner.nextInt();
        scanner.nextLine();

        Optional<Book> bookOpt = books.stream().filter(b -> b.getId() == id).findFirst();
        if (bookOpt.isPresent()) {
            Book updatedBook = BookFactory.createBook(id, title, author, price);
            update(id, updatedBook);
            System.out.println("Sách đã được sửa.");
        } else {
            System.out.println("Không tìm thấy sách với ID này.");
        }
    }

    public void update(int id, Book updatedBook) {
        Optional<Book> bookOpt = books.stream().filter(book -> book.getId() == id).findFirst();
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPrice(updatedBook.getPrice());
            binaryFileStorage.writeBook(books);
            csvFileStorage.writeBook(books);
        }
    }

    public void deleteBook() {
        System.out.print("Nhập ID sách cần xóa: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        delete(id);
        System.out.println("Sách đã được xóa.");
    }

    public void delete(int id) {
        books.removeIf(book -> book.getId() == id);
        binaryFileStorage.writeBook(books);
        csvFileStorage.writeBook(books);
    }

    public void showBooks() {
        if (books.isEmpty()) {
            System.out.println("Không có sách nào.");
        } else {
            System.out.println("Danh sách sách:");
            books.forEach(System.out::println);
        }
    }

    // Member-related methods
    public void addMember() {
        System.out.print("Nhập ID thành viên: ");
        String memberId = scanner.nextLine();
        System.out.print("Nhập tên thành viên: ");
        String name = scanner.nextLine();

        // Create member using factory
        Member member = MemberFactory.createMember(memberId, name);
        members.add(member);
        System.out.println("Thành viên đã được thêm.");
    }

    public void showMembers() {
        if (members.isEmpty()) {
            System.out.println("Không có thành viên nào.");
        } else {
            System.out.println("Danh sách thành viên:");
            members.forEach(System.out::println);
        }
    }

    // Borrow and return book methods
    public void borrowBook() {
        System.out.print("Nhập ID thành viên: ");
        String memberId = scanner.nextLine();
        Member member = members.stream().filter(m -> m.getMemberId().equals(memberId)).findFirst().orElse(null);

        if (member == null) {
            System.out.println("Không tìm thấy thành viên.");
            return;
        }

        System.out.print("Nhập ID sách cần mượn: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        Book book = books.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);

        if (book == null) {
            System.out.println("Không tìm thấy sách.");
            return;
        }

        if (book.isBorrowed()) {
            System.out.println("Sách này đã được mượn.");
        } else {
            book.borrow();
            member.borrowBook(book);
            books.remove(book);
            loanRecords.add(new LoanRecord(member, book));
            System.out.println("Mượn sách thành công.");
            csvFileStorage.writeBook(books);


            // Notify observers
            notificationService.notifyObservers("Thành viên " + member.getName() + " đã mượn sách: " + book.getTitle());
        }
    }

    public void returnBook() {
        System.out.print("Nhập ID thành viên: ");
        String memberId = scanner.nextLine();
        Member member = members.stream().filter(m -> m.getMemberId().equals(memberId)).findFirst().orElse(null);

        if (member == null) {
            System.out.println("Không tìm thấy thành viên.");
            return;
        }

        System.out.print("Nhập ID sách cần trả: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        Book book = member.getBorrowedBooks().stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);

        if (book == null) {
            System.out.println("Sách này không nằm trong danh sách mượn của bạn.");
            return;
        }

        book.returnBook();
        member.returnBook(book);
        books.add(book);
        loanRecords.removeIf(record -> record.getBook().equals(book) && record.getMember().equals(member));
        System.out.println("Trả sách thành công.");
        csvFileStorage.writeBook(books);

        notificationService.notifyObservers("Thành viên " + member.getName() + " đã trả sách: " + book.getTitle());
    }
}
