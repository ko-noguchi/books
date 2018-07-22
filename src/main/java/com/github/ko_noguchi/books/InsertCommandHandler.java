package com.github.ko_noguchi.books;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

import static com.github.ko_noguchi.books.CommandHandlerUtils.writeLine;

public class InsertCommandHandler implements CommandHandler {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("YYYYMMdd HHmmss.SSS");

    private final String registerer;
    private final OutputStream console;
    private final Books books;
    private final Clock clock;
    private final IdGenerator idGenerator;

    InsertCommandHandler(String registerer, OutputStream console, Books books,
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
            Book.Builder bookBuilder = Book.builderForInsertion(argument);
            fillAutoRegisteredItems(bookBuilder);

            books.insert(bookBuilder.build());
            books.commit();

            writeLine(console, "書籍を登録しました。");
        } catch (BookFormatException e) {
            writeLine(console, e.getMessage());
        }
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
