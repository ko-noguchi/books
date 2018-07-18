package com.github.ko_noguchi.books;

public class ExitCommandHandler implements CommandHandler {
  @Override
  public boolean match(String line) {
    return line.equals("exit");
  }

  @Override
  public void handle(String line) {
    System.out.println("Happy happy joy joy.");
    System.exit(0);
  }
}
