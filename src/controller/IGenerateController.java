package controller;
import java.util.List;

public interface IGenerateController<T> {
    List<T> list();


    void showBooks();

    void addBook();

    void updateBook();

    void deleteBook();

    void addMember();

    void showMembers();

    void borrowBook();

    void returnBook();
}
