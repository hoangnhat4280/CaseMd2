
package model;

public class BookLoan {
    private boolean isBorrowed = false; // Trạng thái mượn sách

    public boolean borrow() {
        if (isBorrowed) {
            return false;
        } else {
            isBorrowed = true;
            return true;
        }
    }

    public boolean returnItem() {
        if (isBorrowed) {
            isBorrowed = false;
            return true;
        }
        return false;
    }
}
