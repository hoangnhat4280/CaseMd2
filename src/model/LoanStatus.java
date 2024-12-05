package model;

public class LoanStatus {

    private boolean isBorrowed;

    public boolean borrow() {
        if (!isBorrowed) {
            isBorrowed = true;
            return true;
        }
        return false;
    }

    public boolean returnItem() {
        if (isBorrowed) {
            isBorrowed = false;
            return true;
        }
        return false;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }
}
