package com.github.ko_noguchi.books;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class InsertCommandHandler implements CommandHandler {
  private final Scanner scanner;
  private final OutputStream console;
  private final InputStream registrer;
  private final OutputStream books;

  InsertCommandHandler(
      Scanner scanner, OutputStream console, InputStream registerer, OutputStream books) {
    this.scanner = scanner;
    this.console = console;
    this.registrer = registerer;
    this.books = books;
  }

  @Override
  public boolean match(String line) {
    return line.equals("insert");
  }

  @Override
  public void handle(String line) throws IOException {
    Book.Builder bookBuilder = Book.builder();
    InsertState current = InsertState.ISBN;

    prompt(current);
    while (scanner.hasNextLine()) {
      current = current.next(scanner.nextLine(), bookBuilder);

      if (current == InsertState.COMPLETE) {
        writeToConsole(current.text());
        break;
      }

      if (current.isError()) {
        writeToConsole(current.text() + System.lineSeparator());
        current = current.next("", bookBuilder);
      }

      prompt(current);
    }

    Book book = bookBuilder.build();
    books.write((book + System.lineSeparator()).getBytes());
  }

  private void prompt(InsertState state) throws IOException {
    writeToConsole(state.text() + ": ");
  }

  private void writeToConsole(String text) throws IOException {
    console.write(text.getBytes());
  }
}
