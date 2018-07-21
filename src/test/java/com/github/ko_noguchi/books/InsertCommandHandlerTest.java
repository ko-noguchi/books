package com.github.ko_noguchi.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InsertCommandHandlerTest {
  private ByteArrayOutputStream consoleSpy;
  private ByteArrayOutputStream booksSpy;
  private ClockStub clockStub;
  private IdGeneratorStub idGeneratorStub;

  @BeforeEach
  void setUp() {
    consoleSpy = new ByteArrayOutputStream();
    booksSpy = new ByteArrayOutputStream();
    clockStub = new ClockStub(LocalDateTime.of(2018, 7, 19, 19, 40, 23, 123_000_000));
    idGeneratorStub = new IdGeneratorStub("stub-id");
  }

  @Test
  void match_matchesStringStartingWithInsertAndSpace() {
    CommandHandler sut =
        new InsertCommandHandler("", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    assertThat(sut.match("insert something")).isTrue();
  }

  @Test
  void match_matchesIgnoringCase() {
    CommandHandler sut =
        new InsertCommandHandler("", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    assertThat(sut.match("inSerT something")).isTrue();
  }

  @Test
  void match_doesNotMatchOtherThanInsert() {
    CommandHandler sut =
        new InsertCommandHandler("", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    assertThat(sut.match("not insert")).isFalse();
  }

  @Test
  void handle_handlesValidInput() throws IOException {
    CommandHandler sut =
        new InsertCommandHandler("Registererです。", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    sut.handle("insert '9784866211305','これは本です','これは著者です','犬々社','20180322',1620");


    assertThat(consoleSpy.toString()).isEqualTo("書籍を登録しました。" + System.lineSeparator());
    assertThat(booksSpy.toString()).isEqualTo(
        "'stub-id','9784866211305','これは本です','これは著者です','犬々社','20180322',1620," +
            "'Registererです。','20180719 194023.123','Registererです。','20180719 194023.123'" +
            System.lineSeparator());
  }

  @Test
  void handle_handlesInvalidInput() throws IOException {
    CommandHandler sut =
        new InsertCommandHandler("Registererです。", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    sut.handle("insert invalid-string");


    assertThat(consoleSpy.toString()).startsWith("本の情報は次の形式で入力してください");
    assertThat(booksSpy.toString()).isEmpty();
  }
}
