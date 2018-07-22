package com.github.ko_noguchi.books;

import java.io.IOException;
import java.io.OutputStream;

class CommandHandlerUtils {
    private CommandHandlerUtils() {
    }

    static void writeLine(OutputStream os, String text) throws IOException {
        os.write((text + System.lineSeparator()).getBytes());
    }
}
