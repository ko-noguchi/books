package com.github.ko_noguchi.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class InsertCommandHandlerTest {
  private ByteArrayOutputStream consoleSpy;
  private ByteArrayOutputStream booksSpy;
  private LocalDateTime now;
  private ClockStub clockStub;
  private IdGeneratorStub idGeneratorStub;

  @BeforeEach
  void setUp() {
    consoleSpy = new ByteArrayOutputStream();
    booksSpy = new ByteArrayOutputStream();
    now = LocalDateTime.of(2018, 7, 19, 19, 40, 23, 123_000_000);
    clockStub = new ClockStub(now);
    idGeneratorStub = new IdGeneratorStub("stub-id");
  }

  @Test
  void match_matchesInsert() {
    CommandHandler sut = new InsertCommandHandler(
            new Scanner(""), "", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    assertThat(sut.match("insert")).isTrue();
  }

  @Test
  void match_matchesInsertIgnoringCase() {
    CommandHandler sut = new InsertCommandHandler(
            new Scanner(""), "", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    assertThat(sut.match("inSerT")).isTrue();
  }

  @Test
  void match_doesNotMatchOtherThanInsert() {
    CommandHandler sut = new InsertCommandHandler(
            new Scanner(""), "", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    assertThat(sut.match("not insert")).isFalse();
  }

  @Test
  void handle_handlesValidInputs() throws IOException {
    handle_handlesInsertion(
        asList(
            "9784866211305",
            "これは本です",
            "これは著者です",
            "犬々社",
            "20180322",
            "1620"),
        asList("ISBNを入力",
            "書籍名を入力",
            "著者名を入力",
            "出版社を入力",
            "出版日を入力",
            "価格を入力",
            "書籍を登録"),
        "'stub-id','9784866211305','これは本です','これは著者です','犬々社','20180322',1620," +
                "'Registererです。','20180719 194023.123','Registererです。','20180719 194023.123'" +
                System.lineSeparator());
  }

  @Test
  void handle_handlesInvalidInputs() throws IOException {
    String invalidIsbn = "THIS_IS_INVALID_ISBN";
    String invalidPublicationDate = "INVALID_PUBLICATION_DATE";
    String invalidPrice = "INVALID_PRICE";

    handle_handlesInsertion(
        asList(
            invalidIsbn,
            "9784866211305",
            "これは本です",
            "これは著者です",
            "犬々社",
            invalidPublicationDate,
            "20180322",
            invalidPrice,
            "1620",
            "trailing_input"),
        asList("ISBNを入力",
            "ISBNは",
            "ISBNを入力",
            "書籍名を入力",
            "著者名を入力",
            "出版社を入力",
            "出版日を入力",
            "出版日は",
            "出版日を入力",
            "価格を入力",
            "価格は",
            "価格を入力",
            "書籍を登録"),
            "'stub-id','9784866211305','これは本です','これは著者です','犬々社','20180322',1620," +
                    "'Registererです。','20180719 194023.123','Registererです。','20180719 194023.123'" +
                    System.lineSeparator());
  }

  private void handle_handlesInsertion(
      List<String> inputs, List<String> consoleOutputs, String fileOutput) throws IOException {
    String joinedInput = String.join(System.lineSeparator(), inputs);

    CommandHandler sut = new InsertCommandHandler(new Scanner(joinedInput),
            "Registererです。", consoleSpy, booksSpy, clockStub, idGeneratorStub);


    sut.handle("insert");


    String spaceOrNewLine = String.format(" |%s", System.lineSeparator());
    assertThat(consoleSpy.toString().split(spaceOrNewLine)).satisfies(outputs -> {
      assertThat(outputs).hasSameSizeAs(consoleOutputs);

      for (int i = 0; i < outputs.length; i++) {
        assertThat(outputs[i]).startsWith(consoleOutputs.get(i));
      }
    });
    assertThat(booksSpy.toString()).isEqualTo(fileOutput);
  }
}
