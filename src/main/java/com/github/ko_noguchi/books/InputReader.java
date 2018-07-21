package com.github.ko_noguchi.books;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class InputReader {
    private final Consumer<String> defaultHandler;
    private final List<CommandHandler> commandHandlers = new ArrayList<>();

    InputReader(Consumer<String> defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    void next(String line) throws IOException {
        for (CommandHandler handler : commandHandlers) {
            if (handler.match(line)) {
                handler.handle(line);
                return;
            }
        }

        defaultHandler.accept(line);
    }

    void addHandler(CommandHandler commandHandler) {
        commandHandlers.add(commandHandler);
    }
}
