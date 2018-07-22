package com.github.ko_noguchi.books;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.github.ko_noguchi.books.CommandHandlerUtils.writeLine;

class StreamBooks implements Books {
    private final List<Book> books;
    private final ThrowingSupplier<OutputStream> out;

    static Books empty(ThrowingSupplier<OutputStream> out) {
        return new StreamBooks(new ArrayList<>(), out);
    }

    static Books parse(List<String> bookRecords, ThrowingSupplier<OutputStream> out) throws IOException {
        List<Book> books = new ArrayList<>();
        for (String book : bookRecords) {
            books.add(Book.parse(book));
        }
        return new StreamBooks(books, out);
    }

    private StreamBooks(List<Book> books, ThrowingSupplier<OutputStream> out) {
        this.books = books;
        this.out = out;
    }

    public List<Book> getAll() {
        return books;
    }

    public void insert(Book book) {
        books.add(book);
    }

    public boolean delete(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    public void commit() throws IOException {
        try (OutputStream os = out.get()) {
            for (Book book : getAll()) {
                writeLine(os, book.dump());
            }
        }
    }
}
