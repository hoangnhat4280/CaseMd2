
package controller;

import model.Book;

public interface IBookController extends IGenerateController<Book> {

    void add(Book book);

}
