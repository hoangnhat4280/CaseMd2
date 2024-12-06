package model;

public class LoanRecord {
    private Member member;
    private Book book;

    public LoanRecord(Member member, Book book) {
        this.member = member;
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "LoanRecord{" + "member=" + member + ", book=" + book + '}';
    }
}
