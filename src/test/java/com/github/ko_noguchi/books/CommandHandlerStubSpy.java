package com.github.ko_noguchi.books;

class CommandHandlerStubSpy implements CommandHandler {
  String match_argument_line;
  boolean match_returnValue;

  @Override
  public boolean match(String line) {
    match_argument_line = line;
    return match_returnValue;
  }

  String handle_argument_line;

  @Override
  public void handle(String line) {
    handle_argument_line = line;
  }
}
