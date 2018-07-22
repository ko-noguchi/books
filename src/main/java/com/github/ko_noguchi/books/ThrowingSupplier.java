package com.github.ko_noguchi.books;

import java.io.IOException;

public interface ThrowingSupplier<T> {
    T get() throws IOException;
}
