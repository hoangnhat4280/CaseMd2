package view;

import controller.book.BookController;
import controller.book.IBookController;

import java.util.Scanner;

public class Main {
    private static IBookController bookController = new BookController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        while (true) {
            System.out.println("===== Menu =====");
            System.out.println("1. Hiển thị danh sách sách");
            System.out.println("2. Thêm sách");
            System.out.println("3. Sửa sách");
            System.out.println("4. Xóa sách");
            System.out.println("5. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    bookController.showBooks();
                    break;
                case 2:
                    bookController.addBook();
                    break;
                case 3:
                    bookController.updateBook();
                    break;
                case 4:
                    bookController.deleteBook();
                    break;
                case 5:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không phù hợp , vui lòng nhập lại");
            }
        }
    }
}
