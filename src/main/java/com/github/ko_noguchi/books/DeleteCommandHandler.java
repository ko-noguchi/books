package com.github.ko_noguchi.books;

import java.io.IOException;
import java.io.OutputStream;

import static com.github.ko_noguchi.books.CommandHandlerUtils.writeLine;

public class DeleteCommandHandler implements CommandHandler {
    private final OutputStream console;
    private final Books books;

    DeleteCommandHandler(OutputStream console, Books books) {
        this.console = console;
        this.books = books;
    }

    @Override
    public boolean match(String line) {
        String command = line.replaceAll(" .+$", "");
        return command.equalsIgnoreCase("delete");
    }

    @Override
    public void handle(String line) throws IOException {
        String id = line.replaceFirst(".+ ", "");

        boolean deleted = books.delete(id);

        if (deleted) {
            books.commit();
            writeLine(console, "指定されたIDの書籍を削除しました。");
        } else {
            writeLine(console, "指定されたIDの書籍が見つかりませんでした。");
        }
    }
}
