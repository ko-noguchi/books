package com.github.ko_noguchi.books;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InsertCommandHandler implements CommandHandler {
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("YYYYMMdd HHmmss.SSS");

  private final Scanner scanner;
  private final String registerer;
  private final OutputStream console;
  private final OutputStream books;
  private final Clock clock;
  private final IdGenerator idGenerator;

  InsertCommandHandler(
      Scanner scanner, String registerer, OutputStream console, OutputStream books,
      Clock clock, IdGenerator idGenerator) {
    this.scanner = scanner;
    this.registerer = registerer;
    this.console = console;
    this.books = books;
    this.clock = clock;
    this.idGenerator = idGenerator;
  }

  @Override
  public boolean match(String line) {
    return line.equalsIgnoreCase("insert");
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

    appendAutoRegisteredItems(bookBuilder);

    Book book = bookBuilder.build();
    books.write((book.dump() + System.lineSeparator()).getBytes());
  }

  private void appendAutoRegisteredItems(Book.Builder bookBuilder) {
    String now = DATE_TIME_FORMATTER.format(clock.now());

    bookBuilder
            .id(idGenerator.generate16CharacterId())
            .createdBy(registerer)
            .createdAt(now)
            .updatedBy(registerer)
            .updatedAt(now);
  }

  private void prompt(InsertState state) throws IOException {
    writeToConsole(state.text() + ": ");
  }

  private void writeToConsole(String text) throws IOException {
    console.write(text.getBytes());
  }
}
