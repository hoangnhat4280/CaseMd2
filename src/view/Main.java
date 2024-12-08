
package view;

import controller.BookController;
import controller.IBookController;

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
            System.out.println("5. Thêm thành viên");
            System.out.println("6. Hiển thị thành viên");
            System.out.println("7. Mượn sách");
            System.out.println("8. Trả sách");
            System.out.println("9. Thoát");
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
                    bookController.addMember();
                    break;
                case 6:
                    bookController.showMembers();
                    break;
                case 7:
                    bookController.borrowBook();
                    break;
                case 8:
                    bookController.returnBook();
                    break;
                case 9:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không phù hợp , vui lòng nhập lại");
            }
        }
    }
}
