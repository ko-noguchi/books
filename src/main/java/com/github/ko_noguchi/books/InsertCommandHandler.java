package com.github.ko_noguchi.books;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

public class InsertCommandHandler implements CommandHandler {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("YYYYMMdd HHmmss.SSS");

    private final String registerer;
    private final OutputStream console;
    private final OutputStream books;
    private final Clock clock;
    private final IdGenerator idGenerator;

    InsertCommandHandler(String registerer, OutputStream console, OutputStream books,
                         Clock clock, IdGenerator idGenerator) {
        this.registerer = registerer;
        this.console = console;
        this.books = books;
        this.clock = clock;
        this.idGenerator = idGenerator;
    }

    @Override
    public boolean match(String line) {
        String command = line.replaceAll(" .+$", "");
        return command.equalsIgnoreCase("insert");
    }

    @Override
    public void handle(String line) throws IOException {
        String argument = line.replaceFirst(".+ ", "");

        try {
            Book.Builder bookBuilder = Book.builderWith(argument);
            fillAutoRegisteredItems(bookBuilder);

            writeLine(books, bookBuilder.build().dump());
            writeLine(console, "書籍を登録しました。");
        } catch (BookFormatException e) {
            writeLine(console, e.getMessage());
        }
    }

    private static void writeLine(OutputStream os, String text) throws IOException {
        os.write((text + System.lineSeparator()).getBytes());
    }

    private void fillAutoRegisteredItems(Book.Builder bookBuilder) {
        String now = DATE_TIME_FORMATTER.format(clock.now());

        bookBuilder
                .id(idGenerator.generate16CharacterId())
                .createdBy(registerer)
                .createdAt(now)
                .updatedBy(registerer)
                .updatedAt(now);
    }
}
