package controller;

import model.Book;
import model.LoanRecord;
import model.Member;
import storage.IReadWriteFile;
import storage.ReadWriteBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BookController implements IBookController {
    static IReadWriteFile iReadWriteFile = ReadWriteBook.getInstance();
    public static List<Book> books = iReadWriteFile.readBooks();
    private List<Member> members;  // Danh sách thành viên
    private List<LoanRecord> loanRecords;  // Danh sách loanRecords
    private Scanner scanner = new Scanner(System.in);

    // Constructor thêm members và loanRecords nếu cần thiết
    public BookController(List<Member> members, List<LoanRecord> loanRecords) {
        this.members = members;
        this.loanRecords = loanRecords;
    }

    public BookController() {
        // Khởi tạo mặc định nếu chưa có members và loanRecords
        this.members = new ArrayList<>();
        this.loanRecords = new ArrayList<>();
    }
    @Override
    public void borrowBook() {
    }

    @Override
    public void returnBook() {

    }

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

    // Lấy thông tin sách từ người dùng
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


    // Mượn sách cho thành viên
    public void borrowBookForMember() {
        System.out.print("Nhập ID thành viên: ");
        String memberId = scanner.nextLine();
        System.out.print("Nhập ID sách: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Member member = findMemberById(memberId);
        if (member != null) {
            for (Book book : books) {
                if (book.getId() == id && !book.getLoanStatus().isBorrowed()) {
                    member.borrowBook(book);
                    loanRecords.add(new LoanRecord(member, book)); // Lưu lại thông tin mượn sách
                    book.getLoanStatus().borrow(); // Thay đổi trạng thái sách
                    iReadWriteFile.writeBook(books); // Ghi lại danh sách sách sau khi mượn
                    System.out.println("Sách đã được mượn thành công cho thành viên: " + member.getName());
                    return;
                }
            }
            System.out.println("Sách không có sẵn hoặc đã được mượn.");
        } else {
            System.out.println("Không tìm thấy thành viên với ID này.");
        }
    }

    // Trả sách từ thành viên
    public void returnBookFromMember() {
        System.out.print("Nhập ID thành viên: ");
        String memberId = scanner.nextLine();
        System.out.print("Nhập ID sách: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Member member = findMemberById(memberId);
        if (member != null) {
            for (Book book : member.getBorrowedBooks()) {
                if (book.getId() == id) {
                    member.returnBook(book);
                    book.getLoanStatus().returnItem(); // Thay đổi trạng thái sách
                    iReadWriteFile.writeBook(books); // Ghi lại danh sách sách sau khi trả
                    System.out.println("Sách đã được trả thành công.");
                    return;
                }
            }
            System.out.println("Thành viên này không mượn sách này.");
        } else {
            System.out.println("Không tìm thấy thành viên với ID này.");
        }
    }


    // Tìm thành viên
    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}
