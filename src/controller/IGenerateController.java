
package controller;

import java.util.List;

public interface IGenerateController<T> {
    List<T> list();
    void add(T t);
}
