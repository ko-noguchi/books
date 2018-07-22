package com.github.ko_noguchi.books;

import java.io.IOException;
import java.util.List;

public interface Books {
    List<Book> getAll();

    void insert(Book book);

    boolean delete(String id);

    void commit() throws IOException;
}
