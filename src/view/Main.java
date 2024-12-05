package view;

import controller.book.BookController;
import controller.book.IBookController;
import model.Book;

import java.util.Scanner;

public class Main {
    private static IBookController bookController = new BookController();

    public static void main(String[] args) {
        System.out.println("Danh sách sách hiện tại:");
        System.out.println(bookController.list());

        Book book = getNewBook();
        bookController.add(book);

        System.out.println("Danh sách sách sau khi thêm:");
        System.out.println(bookController.list());
    }

    private static Book getNewBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Mời bạn nhập vào id:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Tên sách:");
        String title = scanner.nextLine();

        System.out.println("Tác giả:");
        String author = scanner.nextLine();

        System.out.println("Giá:");
        int price = scanner.nextInt();

        return new Book(id, title, author, price);
    }
}
