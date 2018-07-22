package com.github.ko_noguchi.books;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    private static final String REGISTERER_PATH = "registerer.dat";
    private static final String BOOKS_PATH = "books.csv";
    private static final Consumer<String> DEFAULT_HANDLER = l -> System.out.println("無効な入力です: " + l);

    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader(DEFAULT_HANDLER);
        reader.addHandler(new ExitCommandHandler());

        Books books = StreamBooks.parse(
                Files.readAllLines(Paths.get(BOOKS_PATH)), () -> Files.newOutputStream(Paths.get(BOOKS_PATH)));

        prompt();
        try (Scanner scanner = new Scanner(System.in)) {
            reader.addHandler(new InsertCommandHandler(
                    readRegisterer(), System.out, books, LocalDateTime::now, new UuidIdGenerator()));
            reader.addHandler(new DeleteCommandHandler(System.out, books));

            while (scanner.hasNextLine()) {
                reader.next(scanner.nextLine());

                prompt();
            }
        }
    }

    private static String readRegisterer() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(REGISTERER_PATH));
        return lines.get(0);
    }

    private static void prompt() {
        System.out.print(System.lineSeparator() + "books> ");
    }
}
