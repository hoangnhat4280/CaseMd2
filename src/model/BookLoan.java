package model;

public class BookLoan {
    private boolean isBorrowed = false; // Trạng thái mượn sách

    // Phương thức mượn sách
    public boolean borrow() {
        if (isBorrowed) {
            return false; // Sách đã được mượn
        } else {
            isBorrowed = true;
            return true; // Mượn sách thành công
        }
    }

    // Phương thức trả sách
    public boolean returnItem() {
        if (isBorrowed) {
            isBorrowed = false;
            return true; // Trả sách thành công
        }
        return false; // Không thể trả sách nếu nó chưa được mượn
    }
}