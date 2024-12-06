package controller;

import model.Book;
import model.BookLoan;
import model.LoanRecord;
import model.Member;
import storage.IReadWriteFile;
import storage.CSVReadWriteFile;
import storage.ReadWriteBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BookController implements IBookController {
    // Đọc và ghi từ file nhị phân
    static IReadWriteFile binaryFileStorage = ReadWriteBook.getInstance();

    // Đọc và ghi từ file CSV
    static IReadWriteFile csvFileStorage = new CSVReadWriteFile();

    // Sử dụng list chung để chứa sách
    public static List<Book> books = binaryFileStorage.readBooks();  // Mặc định đọc từ file nhị phân
    private static List<Member> members = new ArrayList<>();
    private static List<LoanRecord> loanRecords = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public List<Book> list() {
        return books;
    }

    @Override
    public void add(Book book) {
        books.add(book);
        // Ghi vào cả hai file CSV và nhị phân
        binaryFileStorage.writeBook(books);
        csvFileStorage.writeBook(books);
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
            // Ghi vào cả hai file CSV và nhị phân
            binaryFileStorage.writeBook(books);
            csvFileStorage.writeBook(books);
        }
    }

    public void delete(int id) {
        books.removeIf(book -> book.getId() == id);
        // Ghi vào cả hai file CSV và nhị phân
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

    public Book getBookFromUser() {
        int id = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Mời bạn nhập vào id: ");
            String input = scanner.nextLine();

            if (input.matches("\\d+")) {
                id = Integer.parseInt(input);
                valid = true;
            } else {
                System.out.println("ID không phải số. Vui lòng nhập lại.");
            }
        }

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
        int id = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Nhập id sách cần sửa: ");
            String input = scanner.nextLine();

            if (input.matches("\\d+")) {
                id = Integer.parseInt(input);
                valid = true;
            } else {
                System.out.println("ID không phải số. Vui lòng nhập lại.");
            }
        }

        System.out.println("Nhập thông tin sách mới:");
        Book updatedBook = getBookFromUser();
        update(id, updatedBook);
        System.out.println("Sách đã được sửa.");
    }

    public void deleteBook() {
        int id = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Nhập id sách cần xóa: ");
            String input = scanner.nextLine();

            if (input.matches("\\d+")) {
                id = Integer.parseInt(input);
                valid = true;
            } else {
                System.out.println("ID không phải số nguyên. Vui lòng nhập lại.");
            }
        }

        delete(id);
        System.out.println("Sách đã được xóa.");
    }

    // Thêm thành viên
    public void addMember() {
        System.out.print("Nhập ID thành viên: ");
        String memberId = scanner.nextLine();
        System.out.print("Nhập tên thành viên: ");
        String name = scanner.nextLine();
        Member member = new Member(memberId, name);
        members.add(member);
        System.out.println("Thành viên đã được thêm.");
    }

    // Hiển thị thành viên
    public void showMembers() {
        if (members.isEmpty()) {
            System.out.println("Không có thành viên nào.");
        } else {
            System.out.println("Danh sách thành viên:");
            members.forEach(System.out::println);
        }
    }

    // Mượn sách
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

        if (book.isBorrowed()) {  // Kiểm tra trạng thái sách
            System.out.println("Sách này đã được mượn.");
        } else {
            book.borrow();  // Đánh dấu sách là đã mượn
            member.borrowBook(book);  // Thêm sách vào danh sách mượn của thành viên
            books.remove(book);  // Xóa sách khỏi danh sách sách có sẵn
            loanRecords.add(new LoanRecord(member, book));  // Thêm bản ghi mượn sách
            System.out.println("Mượn sách thành công.");
        }
    }
    // Trả sách
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

        if (book.isBorrowed()) {
            book.returnBook();  // Trả lại sách, đánh dấu là chưa mượn
            member.returnBook(book);  // Xóa sách khỏi danh sách mượn của thành viên
            books.add(book);  // Thêm sách lại vào danh sách sách có sẵn
            loanRecords.removeIf(record -> record.getBook().equals(book) && record.getMember().equals(member));
            System.out.println("Trả sách thành công.");
        } else {
            System.out.println("Không thể trả sách này.");
        }
    }
}
