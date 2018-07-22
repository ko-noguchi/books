package com.github.ko_noguchi.books;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class BooksFakeSpy implements Books {
    private final List<Book> books;

    BooksFakeSpy(Book... books) {
        this.books = new ArrayList<>(asList(books));
    }

    @Override
    public List<Book> getAll() {
        return books;
    }

    @Override
    public void insert(Book book) {
        books.add(book);
    }

    @Override
    public boolean delete(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    List<Book> committedBooks = emptyList();

    @Override
    public void commit() {
        committedBooks = new ArrayList<>(books);
    }
}
