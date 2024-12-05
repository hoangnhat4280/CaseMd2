

package controller;

import model.Book;

import java.util.List;

public interface IGenerateController<T> {
    List<T> list();


    void showBooks();

    void addBook();

    void updateBook();

    void deleteBook();

    void borrowBook();

    void returnBook();

    void borrowBookForMember();

    void returnBookFromMember();
}
