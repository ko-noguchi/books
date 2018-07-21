package com.github.ko_noguchi.books;

import java.io.IOException;

public interface CommandHandler {
    boolean match(String line);

    void handle(String line) throws IOException;
}
