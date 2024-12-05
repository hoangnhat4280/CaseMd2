package model;

import model.Book;
import model.Member;

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

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}