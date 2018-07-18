package com.github.ko_noguchi.books;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Consumer;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.READ;

public class Main {
  private static final String REGISTERER_PATH = "registerer.dat";
  private static final String BOOKS_PATH = "books.dat";
  private static final Consumer<String> DEFAULT_HANDLER = l -> System.out.println("無効な入力です: " + l);

  public static void main(String[] args) throws IOException {
    InputReader reader = new InputReader(DEFAULT_HANDLER);
    reader.addHandler(new ExitCommandHandler());

    prompt();
    try (InputStream registerer = Files.newInputStream(Paths.get(REGISTERER_PATH), READ);
         OutputStream books = Files.newOutputStream(Paths.get(BOOKS_PATH), APPEND)) {
      try (Scanner scanner = new Scanner(System.in)) {
        reader.addHandler(new InsertCommandHandler(scanner, System.out, registerer, books));

        while (scanner.hasNextLine()) {
          reader.next(scanner.nextLine());

          prompt();
        }
      }
    }
  }

  private static void prompt() {
    System.out.print(System.lineSeparator() + "books> ");
  }
}
