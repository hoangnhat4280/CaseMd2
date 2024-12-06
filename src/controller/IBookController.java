

package controller;

import controller.IGenerateController;
import model.Book;

public interface IBookController extends IGenerateController<Book> {

    void add(Book book);

}
