
package view;

import controller.BookController;
import controller.IBookController;
import model.LoanRecord;
import model.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Member> members = new ArrayList<>();  // Danh sách thành viên
    private static List<LoanRecord> loanRecords = new ArrayList<>();  // Danh sách bản ghi mượn sách
    private static IBookController bookController = new BookController(members, loanRecords);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Thêm một số thành viên vào
        members.add(new Member("M001", "Nguyễn Văn A"));
        members.add(new Member("M002", "Trần Thị B"));
        int choice;

        while (true) {
            System.out.println("===== Menu =====");
            System.out.println("1. Hiển thị danh sách sách");
            System.out.println("2. Thêm sách");
            System.out.println("3. Sửa sách");
            System.out.println("4. Xóa sách");
            System.out.println("5. Thoát");
            System.out.println("6. Mượn sách");
            System.out.println("7. Trả sách");
            System.out.println("8. Mượn sách cho thành viên");
            System.out.println("9. Trả sách từ thành viên");
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
                case 6:
                    bookController.borrowBook();
                    break;
                case 7:
                    bookController.returnBook();
                    break;
                case 8:
                    bookController.borrowBookForMember();
                    break;
                case 9:
                    bookController.returnBookFromMember();
                    break;
                default:
                    System.out.println("Lựa chọn không phù hợp , vui lòng nhập lại");
            }
        }
    }
}
